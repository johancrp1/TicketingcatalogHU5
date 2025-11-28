package com.example.ticketingcatalog.domain.service;

import com.example.ticketingcatalog.domain.model.EventModel;
import com.example.ticketingcatalog.domain.ports.out.EventRepositoryPort;
import com.example.ticketingcatalog.infrastructure.exception.DuplicateResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventDomainService implements IEventService {

    @Autowired
    private EventRepositoryPort eventRepository;

    @Override
    public List<EventModel> getAll() {
        return eventRepository.findAll();
    }

    @Override
    public Optional<EventModel> getById(Long id) {
        return eventRepository.findById(id);
    }

    @Override
    public EventModel create(EventModel event) {
        if (event.getName() != null && eventRepository.existsByNameIgnoreCase(event.getName())) {
            throw new DuplicateResourceException("El nombre del evento ya existe");
        }
        return eventRepository.save(event);
    }

    @Override
    public Optional<EventModel> update(Long id, EventModel newEvent) {
        return eventRepository.findById(id).map(existing -> {
            existing.setName(newEvent.getName());
            existing.setDate(newEvent.getDate());
            existing.setVenue(newEvent.getVenue());
            return eventRepository.save(existing);
        });
    }

    @Override
    public boolean delete(Long id) {
        if (eventRepository.findById(id).isPresent()) {
            eventRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
