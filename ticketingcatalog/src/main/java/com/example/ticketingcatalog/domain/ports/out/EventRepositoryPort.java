package com.example.ticketingcatalog.domain.ports.out;

import com.example.ticketingcatalog.domain.model.EventModel;
import java.util.List;
import java.util.Optional;

public interface EventRepositoryPort {

    boolean existsByNameIgnoreCase(String name);
    Optional<EventModel> findById(Long id);
    List<EventModel> findAll();
    EventModel save(EventModel event);
    void deleteById(Long id);
}
