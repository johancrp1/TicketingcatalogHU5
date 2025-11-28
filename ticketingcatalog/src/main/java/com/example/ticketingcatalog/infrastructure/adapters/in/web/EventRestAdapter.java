package com.example.ticketingcatalog.infrastructure.adapters.in.web;

import com.example.ticketingcatalog.application.dto.EventDTO;
import com.example.ticketingcatalog.application.usecase.EventUseCase;
import com.example.ticketingcatalog.infrastructure.exception.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventRestAdapter {

    @Autowired
    private EventUseCase eventUseCase;

    @GetMapping
    public List<EventDTO> getAll() {
        return eventUseCase.getAll();
    }

    @GetMapping("/{id}")
    public EventDTO getById(@PathVariable Long id) {
        return eventUseCase.getByIdDTO(id)
                .orElseThrow(() -> new NotFoundException("Evento no encontrado con id " + id));
    }

    @PostMapping
    public EventDTO create(@Valid @RequestBody EventDTO dto) {
        return eventUseCase.createDTO(dto);
    }

    @PutMapping("/{id}")
    public EventDTO update(@PathVariable Long id, @Valid @RequestBody EventDTO dto) {
        return eventUseCase.updateDTO(id, dto)
                .orElseThrow(() -> new NotFoundException("No se pudo actualizar. Evento no encontrado con id " + id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        boolean deleted = eventUseCase.deleteEvent(id);
        if (!deleted) {
            throw new NotFoundException("No se pudo eliminar. Evento no encontrado con id " + id);
        }
    }
}
