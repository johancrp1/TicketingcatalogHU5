package com.example.ticketingcatalog.domain.model;

import java.util.ArrayList;
import java.util.List;

public class VenueModel {

    private Long id;
    private String name;
    private String city;
    private List<EventModel> events = new ArrayList<>();

    public VenueModel(Long id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public List<EventModel> getEvents() { return events; }
    public void setEvents(List<EventModel> events) { this.events = events; }

    // Métodos de conveniencia para mantener la relación bidireccional
    public void addEvent(EventModel event) {
        events.add(event);
        event.setVenue(this);
    }

    public void removeEvent(EventModel event) {
        events.remove(event);
        event.setVenue(null);
    }
}
