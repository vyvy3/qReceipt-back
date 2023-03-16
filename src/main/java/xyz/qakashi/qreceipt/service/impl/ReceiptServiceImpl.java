package xyz.qakashi.qreceipt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import org.springframework.stereotype.Service;
import xyz.qakashi.qreceipt.config.exception.ServerException;
import xyz.qakashi.qreceipt.domain.ReceiptForm;
import xyz.qakashi.qreceipt.domain.ReceiptTemplate;
import xyz.qakashi.qreceipt.domain.qReceipt;
import xyz.qakashi.qreceipt.repository.ReceiptFormRepository;
import xyz.qakashi.qreceipt.repository.qReceiptRepository;
import xyz.qakashi.qreceipt.service.FileService;
import xyz.qakashi.qreceipt.service.ReceiptService;
import xyz.qakashi.qreceipt.web.dto.ReceiptMainDataDto;
import xyz.qakashi.qreceipt.web.dto.qReceiptViewDto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class ReceiptServiceImpl implements ReceiptService {
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private final ReceiptFormRepository receiptFormRepository;
    private final FileService fileService;
    private final qReceiptRepository qReceiptRepository;


    @Override
    @SneakyThrows
    public qReceiptViewDto generateReceipt (Map<String, Double> products, String authorName) {
    ReceiptForm form = receiptFormRepository.findById(1L).orElse(null);
        if (isNull(form)) {
            throw ServerException.noReceiptFormIsPresent();
        }
        List<JasperPrint> printList = new ArrayList<>();

        for (ReceiptTemplate template : form.getTemplates()) {
            Map<String, Object> parameters = fillParameters(template, products);

            JasperReport report = JasperCompileManager.compileReport(getJrxml(template));
            JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
            printList.add(print);
        }
        byte[] result = getReceiptPages(printList);
        String fileName = "Receipt-" + LocalDate.now().toString() + ".pdf";
        UUID fileUUID = fileService.save(fileName, result);
        qReceipt qReceipt = new qReceipt();
        qReceipt.setAuthor(authorName);
        qReceipt.setPrintDate(ZonedDateTime.now());
        qReceipt.setFileUUID(fileUUID);
        qReceipt = qReceiptRepository.save(qReceipt);
        return new qReceiptViewDto(qReceipt);
    }

    @Override
    public List<qReceiptViewDto> getAllByAuthor(String author) {
        return qReceiptRepository.findAllByAuthor(author).stream()
                .map(qReceiptViewDto::new)
                .collect(Collectors.toList());
    }


    private Map<String, Object> fillParameters (ReceiptTemplate template, Map<String, Double> products) {

        Map<String, Object> parameters = new HashMap<>();

        for (String param : template.getParamNames()) {

            switch (param) {
                case "date":
                    parameters.put(param, simpleDateFormat.format(ZonedDateTime.now()));
                    break;

                case "main":
                    int cnt = 1;
                    List<ReceiptMainDataDto> mainInfo = new ArrayList<>();

                    for (Map.Entry<String, Double> entry : products.entrySet()) {
                        mainInfo.add(new ReceiptMainDataDto(String.valueOf(cnt), entry.getKey(), entry.getValue().toString()));
                        cnt++;
                    }
                    JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(mainInfo);
                    parameters.put(param, dataSource);
                    break;
            }

        }
        return parameters;
    }

    private InputStream getJrxml(ReceiptTemplate template) {
        return new ByteArrayInputStream(template.getValue().getBytes(StandardCharsets.UTF_8));
    }

    @SneakyThrows
    private byte[] getReceiptPages (List<JasperPrint> printList) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, printList);
        exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, Boolean.TRUE);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, os);
        exporter.exportReport();
        return os.toByteArray();
    }
}
