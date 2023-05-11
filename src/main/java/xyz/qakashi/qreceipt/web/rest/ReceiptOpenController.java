package xyz.qakashi.qreceipt.web.rest;

import io.swagger.annotations.ApiModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.qakashi.qreceipt.service.ReceiptService;
import xyz.qakashi.qreceipt.web.dto.ReceiptRegistryDto;

import java.util.List;
import java.util.UUID;

import static xyz.qakashi.qreceipt.util.Constants.PUBLIC_API_ENDPOINT;

@RestController
@RequestMapping(PUBLIC_API_ENDPOINT + "/receipt")
@RequiredArgsConstructor
@ApiModel(value = "ReceiptOpenController", description = "TEST")
//TODO: REMOVE ME
public class ReceiptOpenController {
    private final ReceiptService receiptService;

//    @PostMapping("/generate")
//    public ResponseEntity<qReceiptViewDto> generateReceipt(@ApiParam("Purchased products") @RequestBody Map<String, Double> products) {
//        return ResponseEntity.ok(receiptService.generateReceipt(products, null));
//    }

    @GetMapping("/author/getAll")
    public ResponseEntity<List<ReceiptRegistryDto>> getAll() {
        return ResponseEntity.ok(receiptService.getAllByCashier(null));
    }


    @GetMapping(value = "/qr/{uuid}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable("uuid") UUID id) {
        return receiptService.getReceiptQR(id);
    }

}
