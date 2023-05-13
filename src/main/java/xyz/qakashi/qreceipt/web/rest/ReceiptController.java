package xyz.qakashi.qreceipt.web.rest;

import io.swagger.annotations.ApiModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import xyz.qakashi.qreceipt.service.ReceiptService;
import xyz.qakashi.qreceipt.web.dto.PageDto;
import xyz.qakashi.qreceipt.web.dto.PageableDto;
import xyz.qakashi.qreceipt.web.dto.receipt.ReceiptDetailedDto;
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

    @PostMapping("/owner/getPageable")
    public ResponseEntity<PageDto<ReceiptRegistryDto>> getAllPageable(@ApiIgnore @Autowired Principal principal,
                                                              @RequestBody PageableDto pageable) {
        return ResponseEntity.ok(receiptService.getAllByOwnerPageable(principal.getName(), pageable));
    }

    @PostMapping("/claim/{uuid}")
    public void claimReceipt(@PathVariable("uuid") UUID id,
                                       @ApiIgnore @Autowired Principal principal) {
        receiptService.assignReceipt(principal.getName(), id);
    }

    @GetMapping("/printAndDownloadReceipt/{uuid}")
    public ResponseEntity<Resource> printAndDownloadReceipt(@PathVariable("uuid") UUID id) {
        return receiptService.printAndDownloadReceipt(id);
    }

    @GetMapping("/preview/{uuid}")
    public ResponseEntity<ReceiptDetailedDto> preview(@PathVariable("uuid") UUID id) {
        return ResponseEntity.ok(receiptService.previewReceipt(id));
    }
}
