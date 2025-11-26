package com.example.ticketingcatalog.infrastructure.repository;

import com.example.ticketingcatalog.domain.model.EventModel;
import com.example.ticketingcatalog.domain.model.VenueModel;
import com.example.ticketingcatalog.domain.repository.EventRepository;
import com.example.ticketingcatalog.infrastructure.entity.EventEntity;
import com.example.ticketingcatalog.infrastructure.entity.VenueEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class EventJpaRepository implements EventRepository {

    @Autowired
    private IEventRepository eventJpa;

    @Autowired
    private IVenueRepository venueJpa;

    @Override
    public boolean existsByNameIgnoreCase(String name) {
        return eventJpa.existsByNameIgnoreCase(name);
    }

    @Override
    public Optional<EventModel> findById(Long id) {
        return eventJpa.findById(id)
                .map(e -> new EventModel(
                        e.getId(),
                        e.getName(),
                        e.getDate(),
                        e.getVenue() != null ? new VenueModel(
                                e.getVenue().getId(),
                                e.getVenue().getName(),
                                e.getVenue().getCity()
                        ) : null
                ));
    }

    @Override
    public List<EventModel> findAll() {
        return eventJpa.findAll()
                .stream()
                .map(e -> new EventModel(
                        e.getId(),
                        e.getName(),
                        e.getDate(),
                        e.getVenue() != null ? new VenueModel(
                                e.getVenue().getId(),
                                e.getVenue().getName(),
                                e.getVenue().getCity()
                        ) : null
                ))
                .collect(Collectors.toList());
    }

    @Override
    public EventModel save(EventModel model) {
        EventEntity entity = new EventEntity();
        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setDate(model.getDate());

        if (model.getVenue() != null) {
            entity.setVenue(venueJpa.findById(model.getVenue().getId()).orElse(null));
        }

        EventEntity saved = eventJpa.save(entity);

        return new EventModel(
                saved.getId(),
                saved.getName(),
                saved.getDate(),
                saved.getVenue() != null ? new VenueModel(
                        saved.getVenue().getId(),
                        saved.getVenue().getName(),
                        saved.getVenue().getCity()
                ) : null
        );
    }

    @Override
    public void deleteById(Long id) {
        eventJpa.deleteById(id);
    }
}
