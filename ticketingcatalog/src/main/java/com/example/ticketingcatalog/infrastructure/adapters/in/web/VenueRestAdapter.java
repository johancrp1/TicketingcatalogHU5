package com.example.ticketingcatalog.infrastructure.adapters.in.web;

import com.example.ticketingcatalog.application.dto.VenueDTO;
import com.example.ticketingcatalog.application.usecase.VenueUseCase;
import com.example.ticketingcatalog.infrastructure.exception.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venues")
public class VenueRestAdapter {

    @Autowired
    private VenueUseCase venueUseCase;

    @GetMapping
    public List<VenueDTO> getAll() {
        return venueUseCase.getAllDTO();
    }

    @GetMapping("/{id}")
    public VenueDTO getById(@PathVariable Long id) {
        return venueUseCase.getByIdDTO(id)
                .orElseThrow(() -> new NotFoundException("Venue no encontrado con id " + id));
    }

    @PostMapping
    public VenueDTO create(@Valid @RequestBody VenueDTO dto) {
        return venueUseCase.createDTO(dto);
    }

    @PutMapping("/{id}")
    public VenueDTO update(@PathVariable Long id, @Valid @RequestBody VenueDTO dto) {
        return venueUseCase.updateDTO(id, dto)
                .orElseThrow(() -> new NotFoundException("No se pudo actualizar. Venue no encontrado con id " + id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        boolean deleted = venueUseCase.deleteVenue(id);
        if (!deleted) {
            throw new NotFoundException("No se pudo eliminar. Venue no encontrado con id " + id);
        }
    }
}
