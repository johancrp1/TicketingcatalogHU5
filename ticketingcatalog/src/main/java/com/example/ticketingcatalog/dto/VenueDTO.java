package com.example.ticketingcatalog.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Lombok genera getters, setters, constructores, toString, etc.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VenueDTO {

    private Long id; // ID único del venue

    @NotBlank(message = "El nombre del venue no puede estar vacío")
    private String name; // Nombre del venue

    private String location; // Ubicación del venue
}
