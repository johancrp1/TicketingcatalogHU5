package com.example.ticketingcatalog.infrastructure.repository;

import com.example.ticketingcatalog.domain.model.VenueModel;
import com.example.ticketingcatalog.domain.repository.VenueRepository;
import com.example.ticketingcatalog.infrastructure.entity.VenueEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class VenueJpaRepository implements VenueRepository {

    @Autowired
    private IVenueRepository venueJpa;

    @Override
    public boolean existsByName(String name) {
        return venueJpa.existsByName(name);
    }

    @Override
    public Optional<VenueModel> findById(Long id) {
        return venueJpa.findById(id)
                .map(e -> new VenueModel(e.getId(), e.getName(), e.getCity()));
    }

    @Override
    public List<VenueModel> findAll() {
        return venueJpa.findAll()
                .stream()
                .map(e -> new VenueModel(e.getId(), e.getName(), e.getCity()))
                .collect(Collectors.toList());
    }

    @Override
    public VenueModel save(VenueModel model) {
        VenueEntity entity = new VenueEntity();
        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setCity(model.getCity());

        VenueEntity saved = venueJpa.save(entity);

        return new VenueModel(saved.getId(), saved.getName(), saved.getCity());
    }

    @Override
    public void deleteById(Long id) {
        venueJpa.deleteById(id);
    }
}
