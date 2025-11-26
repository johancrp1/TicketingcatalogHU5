package com.example.ticketingcatalog.infrastructure.controller;

import com.example.ticketingcatalog.application.dto.VenueDTO;
import com.example.ticketingcatalog.application.usecase.VenueUseCase;
import com.example.ticketingcatalog.infrastructure.exception.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venues")
public class VenueController {

    @Autowired
    private VenueUseCase venueUseCase;

    @GetMapping
    public List<VenueDTO> getAll() {
        return venueUseCase.getAll();
    }

    @GetMapping("/{id}")
    public VenueDTO getById(@PathVariable Long id) {
        return venueUseCase.getById(id)
                .orElseThrow(() -> new NotFoundException("Venue no encontrado con id " + id));
    }

    @PostMapping
    public VenueDTO create(@Valid @RequestBody VenueDTO dto) {
        return venueUseCase.create(dto);
    }

    @PutMapping("/{id}")
    public VenueDTO update(@PathVariable Long id, @Valid @RequestBody VenueDTO dto) {
        return venueUseCase.update(id, dto)
                .orElseThrow(() -> new NotFoundException("No se pudo actualizar. Venue no encontrado con id " + id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        boolean deleted = venueUseCase.delete(id);
        if (!deleted) {
            throw new NotFoundException("No se pudo eliminar. Venue no encontrado con id " + id);
        }
    }
}
