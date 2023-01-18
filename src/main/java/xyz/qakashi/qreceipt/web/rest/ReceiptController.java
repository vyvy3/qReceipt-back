package xyz.qakashi.qreceipt.web.rest;

import io.swagger.annotations.ApiModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import xyz.qakashi.qreceipt.service.ReceiptService;
import xyz.qakashi.qreceipt.web.dto.ReceiptGenerateDto;
import xyz.qakashi.qreceipt.web.dto.qReceiptViewDto;

import java.security.Principal;
import java.util.List;

import static xyz.qakashi.qreceipt.util.Constants.PRIVATE_API_ENDPOINT;

@RestController
@RequestMapping(PRIVATE_API_ENDPOINT + "/receipt")
@RequiredArgsConstructor
@ApiModel(value = "ReceiptController", description = "Receipt Controller, receipt generation")
public class ReceiptController {
    private final ReceiptService receiptService;

    @PostMapping("/generate")
    public ResponseEntity<qReceiptViewDto> generateReceipt(@ApiIgnore @Autowired Principal principal,
                                                           @RequestBody ReceiptGenerateDto dto) {
        return ResponseEntity.ok(receiptService.generateReceipt(dto, principal.getName()));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<qReceiptViewDto>> getAll(@ApiIgnore @Autowired Principal principal) {
        return ResponseEntity.ok(receiptService.getAllByAuthor(principal.getName()));
    }
}
