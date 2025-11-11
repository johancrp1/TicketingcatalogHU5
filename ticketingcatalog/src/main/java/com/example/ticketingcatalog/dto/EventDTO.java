package com.example.ticketingcatalog.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Lombok genera automáticamente getters, setters, constructores, toString, etc.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {

    private Long id; // ID único del evento

    @NotBlank(message = "El nombre del evento no puede estar vacío")
    private String name; // Nombre del evento

    private String date; // Fecha del evento (puede ser String por ahora)
    private String venueId; // ID del venue asociado
}
