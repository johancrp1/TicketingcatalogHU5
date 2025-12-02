package com.example.ticketingcatalog.application.mapper;

import com.example.ticketingcatalog.application.dto.VenueDTO;
import com.example.ticketingcatalog.application.dto.EventDTO;
import com.example.ticketingcatalog.domain.model.VenueModel;
import com.example.ticketingcatalog.domain.model.EventModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VenueMapper {

    private final EventMapper eventMapper;

    public VenueMapper(EventMapper eventMapper) {
        this.eventMapper = eventMapper;
    }

    public VenueDTO toDTOFromModel(VenueModel model) {
        VenueDTO dto = new VenueDTO();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setLocation(model.getCity());

        if (model.getEvents() != null) {
            List<EventDTO> eventsDTO = model.getEvents().stream()
                    .map(eventMapper::toDTOFromModel)
                    .collect(Collectors.toList());
            dto.setEvents(eventsDTO);
        }

        return dto;
    }

    public VenueModel toModel(VenueDTO dto) {
        VenueModel model = new VenueModel(dto.getId(), dto.getName(), dto.getLocation());

        if (dto.getEvents() != null) {
            List<EventModel> eventsModel = dto.getEvents().stream()
                    .map(eventMapper::toModel)
                    .collect(Collectors.toList());
            model.setEvents(eventsModel);
            eventsModel.forEach(e -> e.setVenue(model));
        }

        return model;
    }
}
