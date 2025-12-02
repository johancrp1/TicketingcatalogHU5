package com.example.ticketingcatalog.infrastructure.adapters.out.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "venues")
public class VenueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del venue no puede estar vacío")
    @Size(min = 3, max = 60, message = "El nombre debe tener entre 3 y 60 caracteres")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "La ciudad no puede estar vacía")
    @Size(min = 2, max = 40, message = "La ciudad debe tener entre 2 y 40 caracteres")
    @Column(nullable = false)
    private String city;

    // RELACIÓN ONE TO MANY: un Venue puede tener muchos Events
    @OneToMany(
            mappedBy = "venue",      // nombre del atributo en EventEntity
            cascade = CascadeType.ALL,  // persist, merge, remove
            orphanRemoval = true,       // elimina eventos "huérfanos"
            fetch = FetchType.LAZY      // evitar carga innecesaria
    )
    private List<EventEntity> events = new ArrayList<>();

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public List<EventEntity> getEvents() { return events; }
    public void setEvents(List<EventEntity> events) { this.events = events; }

    // Helper para mantener consistencia bidireccional
    public void addEvent(EventEntity event) {
        events.add(event);
        event.setVenue(this);
    }

    public void removeEvent(EventEntity event) {
        events.remove(event);
        event.setVenue(null);
    }
}
