package com.example.ticketingcatalog.domain.service;

import com.example.ticketingcatalog.domain.model.VenueModel;
import com.example.ticketingcatalog.domain.ports.out.VenueRepositoryPort;
import com.example.ticketingcatalog.infrastructure.exception.DuplicateResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VenueDomainService implements IVenueService {

    @Autowired
    private VenueRepositoryPort venueRepository;

    @Override
    public List<VenueModel> getAll() {
        return venueRepository.findAll();
    }

    @Override
    public Optional<VenueModel> getById(Long id) {
        return venueRepository.findById(id);
    }

    @Override
    public VenueModel create(VenueModel venue) {
        if (venue.getName() != null && venueRepository.existsByName(venue.getName())) {
            throw new DuplicateResourceException("El nombre del venue ya existe");
        }
        return venueRepository.save(venue);
    }

    @Override
    public Optional<VenueModel> update(Long id, VenueModel newVenue) {
        return venueRepository.findById(id).map(existing -> {
            existing.setName(newVenue.getName());
            existing.setCity(newVenue.getCity());
            return venueRepository.save(existing);
        });
    }

    @Override
    public boolean delete(Long id) {
        if (venueRepository.findById(id).isPresent()) {
            venueRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
