package xyz.qakashi.qreceipt.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.qakashi.qreceipt.config.exception.NotFoundException;
import xyz.qakashi.qreceipt.domain.Organization;
import xyz.qakashi.qreceipt.repository.OrganizationRepository;
import xyz.qakashi.qreceipt.service.OrganizationService;
import xyz.qakashi.qreceipt.util.Mapper;
import xyz.qakashi.qreceipt.web.dto.organization.OrganizationDto;
import xyz.qakashi.qreceipt.web.dto.organization.OrganizationViewDto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final Mapper mapper;

    @Override
    public Long create(OrganizationDto dto) {
        Organization organization = mapper.toOrganization(dto);
        organization = organizationRepository.save(organization);
        return organization.getId();
    }

    @Override
    public void assignPicture(Long id, UUID picture) {
        Organization organization = organizationRepository.findById(id).orElse(null);
        if (isNull(organization)) {
            throw NotFoundException.entityNotFoundById("Organization", id);
        }
        organization.setPicture(picture);
        organizationRepository.save(organization);
    }

    @Override
    public void update(OrganizationDto dto, Long id) {
        Organization organization = organizationRepository.findById(id).orElse(null);
        if (isNull(organization)) {
            throw NotFoundException.entityNotFoundById("Organization", id);
        }
        organization.setName(dto.getName());
        organization.setPicture(dto.getPicture());
        organization.setDescription(dto.getDescription());
        organization.setMerchandiseCategory(dto.getMerchandiseCategory());
        organizationRepository.save(organization);
    }

    @Override
    public OrganizationViewDto getById(Long id) {
        Organization organization = organizationRepository.findById(id).orElse(null);
        if (isNull(organization)) {
            throw NotFoundException.entityNotFoundById("Organization", id);
        }
        return mapper.toDto(organization);
    }

    @Override
    public List<OrganizationViewDto> getAll() {
        return organizationRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
