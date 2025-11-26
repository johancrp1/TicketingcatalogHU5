package com.example.ticketingcatalog.application.mapper;

import com.example.ticketingcatalog.application.dto.EventDTO;
import com.example.ticketingcatalog.domain.model.EventModel;
import com.example.ticketingcatalog.domain.model.VenueModel;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

    public EventDTO toDTOFromModel(EventModel model) {
        EventDTO dto = new EventDTO();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setDate(model.getDate() != null ? model.getDate().toString() : null);
        dto.setVenueId(model.getVenue() != null ? model.getVenue().getId().toString() : null);
        return dto;
    }

    public EventModel toModel(EventDTO dto) {
        VenueModel venue = dto.getVenueId() != null ?
                new VenueModel(Long.parseLong(dto.getVenueId()), null, null) : null;
        return new EventModel(dto.getId(), dto.getName(),
                dto.getDate() != null ? java.time.LocalDate.parse(dto.getDate()) : null,
                venue);
    }
}
