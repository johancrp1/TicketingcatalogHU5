package com.example.ticketingcatalog.application.usecase;

import com.example.ticketingcatalog.application.dto.EventDTO;
import com.example.ticketingcatalog.application.mapper.EventMapper;
import com.example.ticketingcatalog.domain.model.EventModel;
import com.example.ticketingcatalog.domain.service.EventDomainService;
import com.example.ticketingcatalog.infrastructure.exception.DuplicateResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventUseCase {

    @Autowired
    private EventDomainService eventDomainService;

    @Autowired
    private EventMapper eventMapper;

    public List<EventDTO> getAll() {
        return eventDomainService.getAll()
                .stream()
                .map(eventMapper::toDTOFromModel)
                .collect(Collectors.toList());
    }

    public Optional<EventDTO> getById(Long id) {
        return eventDomainService.getById(id)
                .map(eventMapper::toDTOFromModel);
    }

    public EventDTO create(EventDTO dto) {
        // Validación de duplicado usando dominio
        if (dto.getName() != null &&
                eventDomainService.getAll().stream()
                        .anyMatch(e -> e.getName().equalsIgnoreCase(dto.getName()))) {
            throw new DuplicateResourceException("El nombre del evento ya existe");
        }

        EventModel model = eventMapper.toModel(dto);
        EventModel created = eventDomainService.create(model);
        return eventMapper.toDTOFromModel(created);
    }

    public Optional<EventDTO> update(Long id, EventDTO dto) {
        EventModel model = eventMapper.toModel(dto);
        return eventDomainService.update(id, model)
                .map(eventMapper::toDTOFromModel);
    }

    public boolean delete(Long id) {
        return eventDomainService.delete(id);
    }
}
