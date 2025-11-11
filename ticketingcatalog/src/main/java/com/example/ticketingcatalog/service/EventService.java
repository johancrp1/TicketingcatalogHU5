package com.example.ticketingcatalog.service;

import com.example.ticketingcatalog.dto.EventDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service // Marca la clase como un "servicio" de Spring
public class EventService {

    private final List<EventDTO> events = new ArrayList<>(); // Lista en memoria
    private Long counter = 1L; // Contador para IDs automáticos

    // Devuelve todos los eventos
    public List<EventDTO> getAll() {
        return events;
    }

    // Devuelve un evento por ID
    public Optional<EventDTO> getById(Long id) {
        return events.stream().filter(e -> e.getId().equals(id)).findFirst();
    }

    // Crea un nuevo evento y le asigna un ID automático
    public EventDTO create(EventDTO event) {
        event.setId(counter++);
        events.add(event);
        return event;
    }

    // Actualiza un evento existente
    public Optional<EventDTO> update(Long id, EventDTO newEvent) {
        return getById(id).map(existing -> {
            existing.setName(newEvent.getName());
            existing.setDate(newEvent.getDate());
            existing.setVenueId(newEvent.getVenueId());
            return existing;
        });
    }

    // Elimina un evento por ID
    public boolean delete(Long id) {
        return events.removeIf(e -> e.getId().equals(id));
    }
}
