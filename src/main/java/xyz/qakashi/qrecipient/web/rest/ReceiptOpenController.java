package xyz.qakashi.qrecipient.web.rest;

import io.swagger.annotations.ApiModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.qakashi.qrecipient.service.ReceiptService;
import xyz.qakashi.qrecipient.web.dto.ReceiptGenerateDto;
import xyz.qakashi.qrecipient.web.dto.qReceiptViewDto;

import java.util.List;

import static xyz.qakashi.qrecipient.util.Constants.PUBLIC_API_ENDPOINT;

@RestController
@RequestMapping(PUBLIC_API_ENDPOINT + "/receipt")
@RequiredArgsConstructor
@ApiModel(value = "ReceiptOpenController", description = "TEST")
public class ReceiptOpenController {
    private final ReceiptService receiptService;

    @PostMapping("/generate")
    public ResponseEntity<qReceiptViewDto> generateReceipt(
                                                           @RequestBody ReceiptGenerateDto dto) {
        return ResponseEntity.ok(receiptService.generateReceipt(dto, "string"));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<qReceiptViewDto>> getAll() {
        return ResponseEntity.ok(receiptService.getAllByAuthor("string"));
    }
}
