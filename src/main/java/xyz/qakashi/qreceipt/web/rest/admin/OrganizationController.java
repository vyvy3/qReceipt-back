package xyz.qakashi.qreceipt.web.rest.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.qakashi.qreceipt.service.OrganizationService;
import xyz.qakashi.qreceipt.web.dto.organization.OrganizationDto;

import static xyz.qakashi.qreceipt.util.Constants.ADMIN_API_ENDPOINT;

@RestController
@RequestMapping(ADMIN_API_ENDPOINT + "/organization")
@RequiredArgsConstructor
public class OrganizationController {
    private final OrganizationService organizationService;

    @PostMapping(value = "/create")
    public ResponseEntity<Long> create(@RequestBody OrganizationDto dto) {
        return ResponseEntity.ok(organizationService.create(dto));
    }

    @PutMapping(value = "/update/{id}")
    public void update(@PathVariable("id") Long id,
                       @RequestBody OrganizationDto dto) {
        organizationService.update(dto, id);
    }

    @GetMapping(value = "/get/{id}")
    public void get(@PathVariable("id") Long id) {
        organizationService.getById(id);
    }

    @GetMapping(value = "/getAll")
    public void get() {
        organizationService.getAll();
    }
}
