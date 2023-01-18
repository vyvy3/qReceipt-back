package xyz.qakashi.qreceipt.web.rest;

import io.swagger.annotations.ApiModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.qakashi.qreceipt.service.ReceiptService;
import xyz.qakashi.qreceipt.web.dto.ReceiptGenerateDto;
import xyz.qakashi.qreceipt.web.dto.qReceiptViewDto;

import java.util.List;

import static xyz.qakashi.qreceipt.util.Constants.PUBLIC_API_ENDPOINT;

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
