package xyz.qakashi.qreceipt.web.rest;

import io.swagger.annotations.ApiModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import xyz.qakashi.qreceipt.service.ReceiptService;
import xyz.qakashi.qreceipt.web.dto.receipt.ReceiptRegistryDto;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import static xyz.qakashi.qreceipt.util.Constants.PRIVATE_API_ENDPOINT;

@RestController
@RequestMapping(PRIVATE_API_ENDPOINT + "/receipt")
@RequiredArgsConstructor
@ApiModel(value = "ReceiptController", description = "Receipt Controller, receipt generation")
public class ReceiptController {
    private final ReceiptService receiptService;

    @GetMapping("/owner/getAll")
    public ResponseEntity<List<ReceiptRegistryDto>> getAll(@ApiIgnore @Autowired Principal principal) {
        return ResponseEntity.ok(receiptService.getAllByOwner(principal.getName()));
    }

    @PostMapping("/claim/{uuid}")
    public void claimReceipt(@PathVariable("uuid") UUID id,
                                       @ApiIgnore @Autowired Principal principal) {
        receiptService.assignReceipt(principal.getName(), id);
    }

}
