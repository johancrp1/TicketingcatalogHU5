package com.example.ticketingcatalog.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VenueDTO {

    private Long id;

    @NotBlank(message = "El nombre del venue no pueede estar vacío")
    @Size(min = 3, max = 60, message = "El nombre debe tener entre 3 y 60 caracteres")
    private String name;

    @NotBlank(message = "La ciudad es obligatoria")
    @Size(min = 2, max = 40, message = "La ciudad debe tener entre 2 y 40 caracteres")
    private String location;
}
