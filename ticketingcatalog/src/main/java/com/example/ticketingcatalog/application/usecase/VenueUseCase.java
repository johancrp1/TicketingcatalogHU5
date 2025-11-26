package com.example.ticketingcatalog.application.usecase;

import com.example.ticketingcatalog.application.dto.VenueDTO;
import com.example.ticketingcatalog.application.mapper.VenueMapper;
import com.example.ticketingcatalog.domain.model.VenueModel;
import com.example.ticketingcatalog.domain.service.VenueDomainService;
import com.example.ticketingcatalog.infrastructure.exception.DuplicateResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VenueUseCase {

    @Autowired
    private VenueDomainService venueDomainService;

    @Autowired
    private VenueMapper venueMapper;

    public List<VenueDTO> getAll() {
        return venueDomainService.getAll()
                .stream()
                .map(venueMapper::toDTOFromModel)
                .collect(Collectors.toList());
    }

    public Optional<VenueDTO> getById(Long id) {
        return venueDomainService.getById(id)
                .map(venueMapper::toDTOFromModel);
    }

    public VenueDTO create(VenueDTO dto) {
        if (dto.getName() != null &&
                venueDomainService.getAll().stream()
                        .anyMatch(v -> v.getName().equalsIgnoreCase(dto.getName()))) {
            throw new DuplicateResourceException("El nombre del venue ya existe");
        }

        VenueModel model = venueMapper.toModel(dto);
        VenueModel created = venueDomainService.create(model);
        return venueMapper.toDTOFromModel(created);
    }

    public Optional<VenueDTO> update(Long id, VenueDTO dto) {
        VenueModel model = venueMapper.toModel(dto);
        return venueDomainService.update(id, model)
                .map(venueMapper::toDTOFromModel);
    }

    public boolean delete(Long id) {
        return venueDomainService.delete(id);
    }
}
