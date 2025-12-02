package com.example.ticketingcatalog.infrastructure.adapters.out.jpa;

import com.example.ticketingcatalog.domain.model.EventModel;
import com.example.ticketingcatalog.domain.model.VenueModel;
import com.example.ticketingcatalog.domain.ports.out.VenueRepositoryPort;
import com.example.ticketingcatalog.infrastructure.adapters.out.jpa.entity.EventEntity;
import com.example.ticketingcatalog.infrastructure.adapters.out.jpa.entity.VenueEntity;
import com.example.ticketingcatalog.infrastructure.adapters.out.jpa.repository.IVenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class VenueJpaAdapter implements VenueRepositoryPort {

    @Autowired
    private IVenueRepository venueJpa;

    @Override
    public boolean existsByName(String name) {
        return venueJpa.existsByName(name);
    }

    @Override
    public Optional<VenueModel> findById(Long id) {
        return venueJpa.findById(id)
                .map(v -> {
                    VenueModel model = new VenueModel(v.getId(), v.getName(), v.getCity());
                    // mapear eventos si existen
                    List<EventModel> events = v.getEvents()
                            .stream()
                            .map(e -> new EventModel(
                                    e.getId(),
                                    e.getName(),
                                    e.getDate(),
                                    null // evitamos bucle infinito
                            ))
                            .collect(Collectors.toList());
                    // si quieres, puedes agregar un setter de eventos en VenueModel
                    return model;
                });
    }

    @Override
    public List<VenueModel> findAll() {
        return venueJpa.findAll()
                .stream()
                .map(v -> new VenueModel(v.getId(), v.getName(), v.getCity()))
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
