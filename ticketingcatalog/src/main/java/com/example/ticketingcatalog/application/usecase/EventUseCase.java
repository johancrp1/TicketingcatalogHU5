package com.example.ticketingcatalog.application.usecase;

import com.example.ticketingcatalog.application.dto.EventDTO;
import com.example.ticketingcatalog.application.mapper.EventMapper;
import com.example.ticketingcatalog.domain.model.EventModel;
import com.example.ticketingcatalog.domain.ports.in.EventUseCasePort;
import com.example.ticketingcatalog.domain.service.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventUseCase implements EventUseCasePort {

    @Autowired
    private IEventService eventDomainService;

    @Autowired
    private EventMapper eventMapper;

    // Port methods using domain models
    @Override
    public List<EventModel> getAllEvents() {
        return eventDomainService.getAll();
    }

    @Override
    public Optional<EventModel> getEventById(Long id) {
        return eventDomainService.getById(id);
    }

    @Override
    public EventModel createEvent(EventModel event) {
        return eventDomainService.create(event);
    }

    @Override
    public Optional<EventModel> updateEvent(Long id, EventModel event) {
        return eventDomainService.update(id, event);
    }

    @Override
    public boolean deleteEvent(Long id) {
        return eventDomainService.delete(id);
    }

    // Helper DTO methods used by REST adapter
    public List<EventDTO> getAll() {
        return getAllEvents().stream().map(eventMapper::toDTOFromModel).collect(Collectors.toList());
    }

    public Optional<EventDTO> getByIdDTO(Long id) {
        return getEventById(id).map(eventMapper::toDTOFromModel);
    }

    public EventDTO createDTO(EventDTO dto) {
        EventModel created = createEvent(eventMapper.toModel(dto));
        return eventMapper.toDTOFromModel(created);
    }

    public Optional<EventDTO> updateDTO(Long id, EventDTO dto) {
        return updateEvent(id, eventMapper.toModel(dto)).map(eventMapper::toDTOFromModel);
    }
}
