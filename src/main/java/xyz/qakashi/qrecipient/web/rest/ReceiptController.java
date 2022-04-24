package xyz.qakashi.qrecipient.web.rest;

import io.swagger.annotations.ApiModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.qakashi.qrecipient.service.ReceiptService;
import xyz.qakashi.qrecipient.web.dto.ReceiptGenerateDto;

import static xyz.qakashi.qrecipient.util.Constants.PUBLIC_API_ENDPOINT;

@RestController
@RequestMapping(PUBLIC_API_ENDPOINT + "/receipt")
@RequiredArgsConstructor
@ApiModel(value = "ReceiptController", description = "Receipt Controller, receipt generation")
public class ReceiptController {
    private final ReceiptService receiptService;

    @PostMapping("/generate")
    public ResponseEntity<String> generateReceipt (@RequestBody ReceiptGenerateDto dto) {
        return ResponseEntity.ok(receiptService.generateReceipt(dto));
    }
}
