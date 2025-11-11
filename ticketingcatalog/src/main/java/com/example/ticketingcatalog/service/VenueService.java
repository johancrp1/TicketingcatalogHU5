package com.example.ticketingcatalog.service;

import com.example.ticketingcatalog.dto.VenueDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service // Marca la clase como servicio de Spring
public class VenueService {

    private final List<VenueDTO> venues = new ArrayList<>(); // Lista en memoria de venues
    private Long counter = 1L; // Contador para generar IDs automáticos

    // Devuelve todos los venues
    public List<VenueDTO> getAll() {
        return venues;
    }

    // Devuelve un venue por ID
    public Optional<VenueDTO> getById(Long id) {
        return venues.stream().filter(v -> v.getId().equals(id)).findFirst();
    }

    // Crea un nuevo venue y le asigna un ID automático
    public VenueDTO create(VenueDTO venue) {
        venue.setId(counter++);
        venues.add(venue);
        return venue;
    }

    // Actualiza un venue existente
    public Optional<VenueDTO> update(Long id, VenueDTO newVenue) {
        return getById(id).map(existing -> {
            existing.setName(newVenue.getName());
            existing.setLocation(newVenue.getLocation());
            return existing;
        });
    }

    // Elimina un venue por ID
    public boolean delete(Long id) {
        return venues.removeIf(v -> v.getId().equals(id));
    }
}
