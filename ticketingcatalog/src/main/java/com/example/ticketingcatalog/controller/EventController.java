package com.example.ticketingcatalog.controller;

import com.example.ticketingcatalog.dto.EventDTO;
import com.example.ticketingcatalog.service.interfaces.IEventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Indica que la clase es un controlador REST
@RequestMapping("/events") // Base path para todos los endpoints de eventoss
public class EventController {

    private final IEventService eventService;

    public EventController(IEventService eventService) {
        this.eventService = eventService;
    }

    // GET /events -> devuelve todos los eventos
    @GetMapping
    public ResponseEntity<List<EventDTO>> getAll() {
        return ResponseEntity.ok(eventService.getAll());
    }

    // GET /events/{id} -> devuelve un evento por ID
    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getById(@PathVariable Long id) {
        return eventService.getById(id)
                .map(ResponseEntity::ok) // Devuelve 200 OK con el evento
                .orElse(ResponseEntity.notFound().build()); // 404 si no existe
    }

    // POST /events -> crea un nuevo evento
    @PostMapping
    public ResponseEntity<EventDTO> create(@Valid @RequestBody EventDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.create(dto));
    }


    // PUT /events/{id} -> actualiza un evento existente
    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody EventDTO dto) {
        return eventService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /events/{id} -> elimina un evento por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean removed = eventService.delete(id);
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
