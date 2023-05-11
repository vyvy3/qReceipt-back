package xyz.qakashi.qreceipt.service;

import xyz.qakashi.qreceipt.web.dto.organization.OrganizationDto;
import xyz.qakashi.qreceipt.web.dto.organization.OrganizationViewDto;

import java.util.List;
import java.util.UUID;

public interface OrganizationService {
    Long create(OrganizationDto dto);

    void update(OrganizationDto dto, Long id);

    OrganizationViewDto getById(Long id);

    List<OrganizationViewDto> getAll();

    void assignPicture(Long id, UUID picture);
}
