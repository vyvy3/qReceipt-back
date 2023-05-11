package xyz.qakashi.qreceipt.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xyz.qakashi.qreceipt.config.exception.NotFoundException;
import xyz.qakashi.qreceipt.config.exception.ServerException;
import xyz.qakashi.qreceipt.domain.ReceiptForm;
import xyz.qakashi.qreceipt.domain.ReceiptTemplate;
import xyz.qakashi.qreceipt.domain.User;
import xyz.qakashi.qreceipt.domain.qReceipt;
import xyz.qakashi.qreceipt.repository.ReceiptFormRepository;
import xyz.qakashi.qreceipt.repository.UserRepository;
import xyz.qakashi.qreceipt.repository.qReceiptRepository;
import xyz.qakashi.qreceipt.service.FileService;
import xyz.qakashi.qreceipt.service.ReceiptService;
import xyz.qakashi.qreceipt.web.dto.ReceiptMainDataDto;
import xyz.qakashi.qreceipt.web.dto.qReceiptViewDto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    private final qReceiptRepository receiptRepository;
    private final UserRepository userRepository;
    private final FileService fileService;

    //    @Override
    @SneakyThrows
    public qReceiptViewDto generateReceipt(Map<String, Double> products, String authorName) {
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
        qReceipt.setId(fileUUID);
        qReceipt.setJson(new ObjectMapper().writeValueAsString(products)); //TODO: make it object
        qReceipt.setTotalSum(getTotalSum(products));
        qReceipt = receiptRepository.save(qReceipt);
        return new qReceiptViewDto(qReceipt);
    }

    @Override
    public List<qReceiptViewDto> getAllByAuthor(String author) {
        return receiptRepository.findAllByAuthor(author).stream()
                .map(qReceiptViewDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<qReceiptViewDto> getAllByOwner(String author) {
        return receiptRepository.findAllByOwner_EmailIgnoreCase(author).stream()
                .map(qReceiptViewDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public UUID createReceipt(Map<String, Double> products, String authorName) {
        UUID id = UUID.randomUUID();
        qReceipt qReceipt = new qReceipt();
        qReceipt.setAuthor(authorName);
        qReceipt.setPrintDate(ZonedDateTime.now());
        qReceipt.setId(id);
        try {
            qReceipt.setJson(new ObjectMapper().writeValueAsString(products)); //TODO: make it object
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw ServerException.errorDuringSerialization();
        }
        qReceipt.setTotalSum(getTotalSum(products));
        receiptRepository.save(qReceipt);

        return id;
    }

    @Override
    public ResponseEntity<byte[]> getReceiptQR(UUID id) {
        qReceipt receipt = receiptRepository.findById(id).orElse(null);
        if (isNull(receipt)) {
            throw NotFoundException.entityNotFoundById("qReceipt", id.toString());
        }

        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(id.toString(), BarcodeFormat.QR_CODE, 200, 200);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, MediaType.IMAGE_PNG.getSubtype(), outputStream);
            return ResponseEntity.ok()
                    .contentLength(outputStream.size())
                    .contentType(MediaType.IMAGE_PNG)
                    .body(outputStream.toByteArray());
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            throw ServerException.errorDuringQrGeneration();
        }
    }

    @Override
    public void assignReceipt(String assignTo, UUID receiptId) {
        User user = userRepository.findByEmailIgnoreCase(assignTo).orElse(null);
        if (isNull(user)) {
            throw NotFoundException.userNotFoundByEmail(assignTo);
        }
        qReceipt receipt = receiptRepository.findById(receiptId).orElse(null);
        if (isNull(receipt)) {
            throw NotFoundException.entityNotFoundById("qReceipt", receiptId.toString());
        }

        receipt.setOwnerId(user.getId());
        receiptRepository.save(receipt);
    }


    private Map<String, Object> fillParameters(ReceiptTemplate template, Map<String, Double> products) {

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
    private byte[] getReceiptPages(List<JasperPrint> printList) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, printList);
        exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, Boolean.TRUE);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, os);
        exporter.exportReport();
        return os.toByteArray();
    }

    private Double getTotalSum(Map<String, Double> products) {
        return products.values().stream().mapToDouble(Double::doubleValue).sum();
    }
}
