package xyz.qakashi.qreceipt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import org.springframework.stereotype.Service;
import xyz.qakashi.qreceipt.domain.ReceiptForm;
import xyz.qakashi.qreceipt.domain.ReceiptTemplate;
import xyz.qakashi.qreceipt.domain.enums.FileExtension;
import xyz.qakashi.qreceipt.domain.qReceipt;
import xyz.qakashi.qreceipt.repository.ReceiptFormRepository;
import xyz.qakashi.qreceipt.repository.qReceiptRepository;
import xyz.qakashi.qreceipt.service.FileService;
import xyz.qakashi.qreceipt.service.ReceiptService;
import xyz.qakashi.qreceipt.web.dto.ReceiptGenerateDto;
import xyz.qakashi.qreceipt.web.dto.ReceiptMainDataDto;
import xyz.qakashi.qreceipt.web.dto.qReceiptViewDto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
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
    public qReceiptViewDto generateReceipt(ReceiptGenerateDto dto, String authorName) {
        ReceiptForm form = receiptFormRepository.findById(dto.getFormId()).orElse(null);
        if (isNull(form)) {
            form = receiptFormRepository.getById(1L); //TODO: suppress with error but now is ok
        }
        List<JasperPrint> printList = new ArrayList<>();

        for (ReceiptTemplate template : form.getTemplates()) {
            Map<String, Object> parameters = fillParameters(template, dto);

            JasperReport report = JasperCompileManager.compileReport(getJrxml(template));
            JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
            printList.add(print);
        }
        byte[] result = getReceiptPages(printList);
        String fileName = UUID.randomUUID().toString();
        fileService.saveFile(fileName, FileExtension.PDF, result);
        qReceipt qReceipt = new qReceipt();
        qReceipt.setAuthor(authorName);
        qReceipt.setPrintDate(ZonedDateTime.now());
        qReceipt.setFileName(fileName);
        qReceipt.setExtension(FileExtension.PDF);
        qReceipt = qReceiptRepository.save(qReceipt);
        return new qReceiptViewDto(qReceipt);
    }

    @Override
    public List<qReceiptViewDto> getAllByAuthor(String author) {
        return qReceiptRepository.findAllByAuthor(author).stream()
                .map(qReceiptViewDto::new)
                .collect(Collectors.toList());
    }


    private Map<String, Object> fillParameters (ReceiptTemplate template, ReceiptGenerateDto dto) {

        Map<String, Object> parameters = new HashMap<>();

        for (String param : template.getParamNames()) {

            switch (param) {
                case "date":
                    parameters.put(param, simpleDateFormat.format(ZonedDateTime.now()));
                    break;

                case "main":
                    int cnt = 1;
                    List<ReceiptMainDataDto> mainInfo = new ArrayList<>();

                    for (Map.Entry<String, String> entry : dto.getValues().entrySet()) {
                        mainInfo.add(new ReceiptMainDataDto(String.valueOf(cnt), entry.getKey(), entry.getValue()));
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
