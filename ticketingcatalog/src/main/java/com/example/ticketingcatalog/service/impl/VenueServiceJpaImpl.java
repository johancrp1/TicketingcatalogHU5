package com.example.ticketingcatalog.service.impl;

import com.example.ticketingcatalog.dto.VenueDTO;
import com.example.ticketingcatalog.entity.VenueEntity;
import com.example.ticketingcatalog.exception.DuplicateResourceException;
import com.example.ticketingcatalog.repository.interfaces.IVenueRepository;
import com.example.ticketingcatalog.service.interfaces.IVenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Primary
public class VenueServiceJpaImpl implements IVenueService {

    @Autowired
    private IVenueRepository venueRepository;

    private VenueDTO toDTO(VenueEntity e) {
        VenueDTO dto = new VenueDTO();
        dto.setId(e.getId());
        dto.setName(e.getName());
        dto.setLocation(e.getCity());
        return dto;
    }

    private VenueEntity toEntity(VenueDTO dto) {
        VenueEntity e = new VenueEntity();
        e.setName(dto.getName());
        e.setCity(dto.getLocation());
        return e;
    }

    @Override
    public List<VenueDTO> getAll() {
        return venueRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<VenueDTO> getById(Long id) {
        return venueRepository.findById(id).map(this::toDTO);
    }

    @Override
    public VenueDTO create(VenueDTO dto) {
        if (venueRepository.existsByName(dto.getName())) {
            throw new DuplicateResourceException("El nombre del venue ya existe");
        }
        VenueEntity saved = venueRepository.save(toEntity(dto));
        return toDTO(saved);
    }

    @Override
    public Optional<VenueDTO> update(Long id, VenueDTO dto) {
        return venueRepository.findById(id).map(existing -> {
            existing.setName(dto.getName());
            existing.setCity(dto.getLocation());
            VenueEntity updated = venueRepository.save(existing);
            return toDTO(updated);
        });
    }

    @Override
    public boolean delete(Long id) {
        if (venueRepository.existsById(id)) {
            venueRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
