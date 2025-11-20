package com.example.ticketingcatalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String name; // Nombre del evento

    @NotBlank(message = "La fecha es obligatoria")
    private String date; // Fecha del evento (puede ser String por ahora)

    private String venueId; // ID del venue asociado

}
