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
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
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
import xyz.qakashi.qreceipt.util.PageableUtils;
import xyz.qakashi.qreceipt.web.dto.PageDto;
import xyz.qakashi.qreceipt.web.dto.PageableDto;
import xyz.qakashi.qreceipt.web.dto.receipt.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class ReceiptServiceImpl implements ReceiptService {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private final ReceiptFormRepository receiptFormRepository;
    private final qReceiptRepository receiptRepository;
    private final UserRepository userRepository;
    private final FileService fileService;

    @Override
    public List<ReceiptRegistryDto> getAllByCashier(String cashier) {
        return receiptRepository.findAllByCashier_LoginIgnoreCase(cashier).stream()
                .map(ReceiptRegistryDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReceiptRegistryDto> getAllByOwner(String ownerLogin) {
        return receiptRepository.findAllByOwner_LoginIgnoreCase(ownerLogin).stream()
                .map(ReceiptRegistryDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public PageDto<ReceiptRegistryDto> getAllByOwnerPageable(String ownerLogin, PageableDto pageable) {
        Page<qReceipt> page = receiptRepository.findAllByOwner_LoginIgnoreCase(ownerLogin, PageableUtils.createPageRequest(pageable));
        List<ReceiptRegistryDto> list = page.toList().stream().map(ReceiptRegistryDto::new).collect(Collectors.toList());
        return new PageDto<>(page, list);
    }

    @Override
    public UUID createReceipt(ReceiptCreateDto dto, String cashierLogin) {
        User cashier = userRepository.findByLoginIgnoreCase(cashierLogin).orElse(null);
        if (isNull(cashier)) {
            throw NotFoundException.userNotFoundByLogin(cashierLogin);
        }

        UUID id = UUID.randomUUID();
        qReceipt receipt = new qReceipt();
        receipt.setCashierId(cashier.getId());
        receipt.setOrganizationId(cashier.getOrganizationId());
        receipt.setId(id);
        receipt.setCreatedDate(ZonedDateTime.now());
        receipt.setJson(dto.getProducts());
        receipt.setTotalSum(getTotalSum(dto.getProducts()));
        receiptRepository.save(receipt);

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
        User user = userRepository.findByLoginIgnoreCase(assignTo).orElse(null);
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

    @Override
    public ResponseEntity<Resource> printAndDownloadReceipt(UUID id) {
        qReceipt receipt = receiptRepository.findById(id).orElse(null);
        if (isNull(receipt)) {
            throw NotFoundException.entityNotFoundById("qReceipt", id.toString());
        }
        String fileName = "receipt-" + LocalDate.now().toString() + ".pdf";
        byte[] result = printReceipt(receipt);
        ByteArrayResource resource = new ByteArrayResource(result);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(result.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @Override
    public ReceiptDetailedDto previewReceipt(UUID id) {
        qReceipt receipt = receiptRepository.findById(id).orElse(null);
        if (isNull(receipt)) {
            throw NotFoundException.entityNotFoundById("qReceipt", id.toString());
        }
        return new ReceiptDetailedDto(receipt);
    }

    @SneakyThrows
    private byte[] printReceipt(qReceipt receipt) {
        ReceiptForm form = receiptFormRepository.findById(1L).orElse(null);
        if (isNull(form)) {
            throw ServerException.noReceiptFormIsPresent();
        }
        List<JasperPrint> printList = new ArrayList<>();

        for (ReceiptTemplate template : form.getTemplates()) {
            Map<String, Object> parameters = fillParameters(template, receipt);

            JasperReport report = JasperCompileManager.compileReport(getJrxml(template));
            JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
            printList.add(print);
        }
        byte[] result = getReceiptPages(printList);
        return result;
    }


    private Map<String, Object> fillParameters(ReceiptTemplate template, qReceipt receipt) {

        Map<String, Object> parameters = new HashMap<>();

        for (String param : template.getParamNames()) {

            switch (param) {
                case "date":
                    parameters.put(param, receipt.getCreatedDate().format(formatter));
                    break;
                case "total":
                    parameters.put(param, receipt.getTotalSum().toString());
                    break;
                case "main":
                    int cnt = 1;
                    List<ReceiptPrintTableDto> mainInfo = new ArrayList<>();

                    for (ReceiptCreateFieldDto entry : receipt.getJson()) {
                        mainInfo.add(new ReceiptPrintTableDto(
                                String.valueOf(cnt),
                                entry.getName(),
                                entry.getPrice().toString(),
                                entry.getQuantity().toString(),
                                entry.getTotalPrice().toString()
                                ));
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

    private Double getTotalSum(List<ReceiptCreateFieldDto> products) {
        return products.stream().mapToDouble(ReceiptCreateFieldDto::getTotalPrice).sum();
    }
}
