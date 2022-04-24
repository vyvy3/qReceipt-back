package xyz.qakashi.qrecipient.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import xyz.qakashi.qrecipient.domain.ReceiptForm;
import xyz.qakashi.qrecipient.domain.ReceiptTemplate;
import xyz.qakashi.qrecipient.domain.enums.FileExtension;
import xyz.qakashi.qrecipient.repository.ReceiptFormRepository;
import xyz.qakashi.qrecipient.service.FileService;
import xyz.qakashi.qrecipient.service.ReceiptService;
import xyz.qakashi.qrecipient.web.dto.ReceiptGenerateDto;
import xyz.qakashi.qrecipient.web.dto.ReceiptMainDataDto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.*;

import static java.util.Objects.*;

@Service
@RequiredArgsConstructor
public class ReceiptServiceImpl implements ReceiptService {
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private final ReceiptFormRepository receiptFormRepository;
    private final FileService fileService;


    @Override
    @SneakyThrows
    public String generateReceipt(ReceiptGenerateDto dto) {
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
        return fileName;
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