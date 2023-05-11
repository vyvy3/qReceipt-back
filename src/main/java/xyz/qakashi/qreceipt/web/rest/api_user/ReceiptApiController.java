package xyz.qakashi.qreceipt.web.rest.api_user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import xyz.qakashi.qreceipt.service.ReceiptService;
import xyz.qakashi.qreceipt.web.dto.receipt.ReceiptCreateDto;
import xyz.qakashi.qreceipt.web.dto.receipt.ReceiptRegistryDto;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import static xyz.qakashi.qreceipt.util.Constants.API_USER_API_ENDPOINT;

@RestController
@RequestMapping(API_USER_API_ENDPOINT + "/receipt")
@RequiredArgsConstructor
public class ReceiptApiController {
    private final ReceiptService receiptService;

    @PostMapping("/create")
    public ResponseEntity<UUID> createReceipt(@RequestBody ReceiptCreateDto dto,
                                              @ApiIgnore @Autowired Principal principal) {
        return ResponseEntity.ok(receiptService.createReceipt(dto, principal.getName()));
    }

    @GetMapping( "/qr/{uuid}")
    public ResponseEntity<byte[]> getQr(@PathVariable("uuid") UUID id) {
        return receiptService.getReceiptQR(id);
    }

    @GetMapping("/getAllMy")
    public List<ReceiptRegistryDto> getAllMyReceipts(@ApiIgnore @Autowired Principal principal) {
        return receiptService.getAllByCashier(principal.getName());
    }
}
