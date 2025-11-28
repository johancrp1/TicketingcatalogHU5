package com.example.ticketingcatalog.application.usecase;

import com.example.ticketingcatalog.application.dto.VenueDTO;
import com.example.ticketingcatalog.application.mapper.VenueMapper;
import com.example.ticketingcatalog.domain.model.VenueModel;
import com.example.ticketingcatalog.domain.ports.in.VenueUseCasePort;
import com.example.ticketingcatalog.domain.service.IVenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VenueUseCase implements VenueUseCasePort {

    @Autowired
    private IVenueService venueDomainService;

    @Autowired
    private VenueMapper venueMapper;

    @Override
    public List<VenueModel> getAllVenues() {
        return venueDomainService.getAll();
    }

    @Override
    public Optional<VenueModel> getVenueById(Long id) {
        return venueDomainService.getById(id);
    }

    @Override
    public VenueModel createVenue(VenueModel venue) {
        return venueDomainService.create(venue);
    }

    @Override
    public Optional<VenueModel> updateVenue(Long id, VenueModel venue) {
        return venueDomainService.update(id, venue);
    }

    @Override
    public boolean deleteVenue(Long id) {
        return venueDomainService.delete(id);
    }

    // Helper DTO methods used by REST adapter
    public List<VenueDTO> getAllDTO() {
        return getAllVenues().stream().map(venueMapper::toDTOFromModel).collect(Collectors.toList());
    }

    public Optional<VenueDTO> getByIdDTO(Long id) {
        return getVenueById(id).map(venueMapper::toDTOFromModel);
    }

    public VenueDTO createDTO(VenueDTO dto) {
        VenueModel created = createVenue(venueMapper.toModel(dto));
        return venueMapper.toDTOFromModel(created);
    }

    public Optional<VenueDTO> updateDTO(Long id, VenueDTO dto) {
        return updateVenue(id, venueMapper.toModel(dto)).map(venueMapper::toDTOFromModel);
    }
}
