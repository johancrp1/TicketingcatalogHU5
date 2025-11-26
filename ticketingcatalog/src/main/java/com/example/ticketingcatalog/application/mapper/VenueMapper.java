package com.example.ticketingcatalog.application.mapper;

import com.example.ticketingcatalog.application.dto.VenueDTO;
import com.example.ticketingcatalog.domain.model.VenueModel;
import org.springframework.stereotype.Component;

@Component
public class VenueMapper {

    public VenueDTO toDTOFromModel(VenueModel model) {
        VenueDTO dto = new VenueDTO();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setLocation(model.getCity());
        return dto;
    }

    public VenueModel toModel(VenueDTO dto) {
        return new VenueModel(dto.getId(), dto.getName(), dto.getLocation());
    }
}
