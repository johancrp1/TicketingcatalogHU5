package com.example.ticketingcatalog.service.impl;

import com.example.ticketingcatalog.dto.EventDTO;
import com.example.ticketingcatalog.entity.EventEntity;
import com.example.ticketingcatalog.entity.VenueEntity;
import com.example.ticketingcatalog.exception.DuplicateResourceException;
import com.example.ticketingcatalog.repository.interfaces.IEventRepository;
import com.example.ticketingcatalog.repository.interfaces.IVenueRepository;
import com.example.ticketingcatalog.service.interfaces.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Primary
public class EventServiceJpaImpl implements IEventService {

    @Autowired
    private IEventRepository eventRepository;

    @Autowired
    private IVenueRepository venueRepository;

    // Convertir Entity <-> DTO
    private EventDTO toDTO(EventEntity e) {
        EventDTO dto = new EventDTO();
        dto.setId(e.getId());
        dto.setName(e.getName());
        dto.setDate(e.getDate().toString());
        dto.setVenueId(e.getVenue() != null ? e.getVenue().getId().toString() : null);
        return dto;
    }

    private EventEntity toEntity(EventDTO dto) {
        EventEntity e = new EventEntity();
        e.setName(dto.getName());
        e.setDate(dto.getDate() != null ? LocalDate.parse(dto.getDate()) : null);
        if (dto.getVenueId() != null) {
            Optional<VenueEntity> venue = venueRepository.findById(Long.parseLong(dto.getVenueId()));
            venue.ifPresent(e::setVenue);
        }
        return e;
    }

    @Override
    public List<EventDTO> getAll() {
        return eventRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<EventDTO> getById(Long id) {
        return eventRepository.findById(id).map(this::toDTO);
    }

    @Override
    public EventDTO create(EventDTO dto) {
        if (eventRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new DuplicateResourceException("El nombre del evento ya existe");
        }
        EventEntity saved = eventRepository.save(toEntity(dto));
        return toDTO(saved);
    }

    @Override
    public Optional<EventDTO> update(Long id, EventDTO dto) {
        return eventRepository.findById(id).map(existing -> {
            existing.setName(dto.getName());
            existing.setDate(LocalDate.parse(dto.getDate()));
            if (dto.getVenueId() != null) {
                venueRepository.findById(Long.parseLong(dto.getVenueId()))
                        .ifPresent(existing::setVenue);
            }
            EventEntity updated = eventRepository.save(existing);
            return toDTO(updated);
        });
    }

    @Override
    public boolean delete(Long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
