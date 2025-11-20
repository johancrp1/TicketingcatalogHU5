package com.example.ticketingcatalog.controller;

import com.example.ticketingcatalog.dto.VenueDTO;
import com.example.ticketingcatalog.service.interfaces.IVenueService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/venues") // Base path para todos los endpoints de venues
public class VenueController {

    private final IVenueService venueService; // Inyección del servicio de venues

    public VenueController(IVenueService venueService) {
        this.venueService = venueService;
    }

    // GET /venues -> devuelve todos los venues
    @GetMapping
    public ResponseEntity<List<VenueDTO>> getAll() {
        return ResponseEntity.ok(venueService.getAll());
    }

    // GET /venues/{id} -> devuelve un venue por ID
    @GetMapping("/{id}")
    public ResponseEntity<VenueDTO> getById(@PathVariable Long id) {
        return venueService.getById(id)
                .map(ResponseEntity::ok) // 200 OK con el venue
                .orElse(ResponseEntity.notFound().build()); // 404 si no existe
    }

    // POST /venues -> crea un nuevo venue
    @PostMapping
    public ResponseEntity<VenueDTO> create(@Valid @RequestBody VenueDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(venueService.create(dto));
    }

    // PUT /venues/{id} -> actualiza un venue existente
    @PutMapping("/{id}")
    public ResponseEntity<VenueDTO> update(@PathVariable Long id, @Valid @RequestBody VenueDTO venue) {
        return venueService.update(id, venue)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /venues/{id} -> elimina un venue por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean removed = venueService.delete(id);
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
