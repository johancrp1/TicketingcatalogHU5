package com.example.ticketingcatalog.application.usecase;

import com.example.ticketingcatalog.application.dto.EventDTO;
import com.example.ticketingcatalog.application.dto.VenueDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EventUseCaseTest {

    @Autowired
    private EventUseCase eventUseCase;

    @Autowired
    private VenueUseCase venueUseCase;

    private VenueDTO testVenue;

    @BeforeEach
    void setup() {
        // Crear un venue para asignar a eventos
        testVenue = new VenueDTO(null, "Venue Test", "Bogotá", null);
        testVenue = venueUseCase.createDTO(testVenue);
    }

    @Test
    void testCreateEvent() {
        EventDTO event = new EventDTO(null, "Evento Test", "2025-12-10", testVenue.getId().toString());
        EventDTO created = eventUseCase.createDTO(event);
        assertNotNull(created.getId());
        assertEquals("Evento Test", created.getName());
        assertEquals(testVenue.getId().toString(), created.getVenueId());
    }

    @Test
    void testGetEventById() {
        EventDTO event = new EventDTO(null, "Evento Test", "2025-12-10", testVenue.getId().toString());
        EventDTO created = eventUseCase.createDTO(event);
        EventDTO fetched = eventUseCase.getByIdDTO(created.getId()).orElse(null);
        assertNotNull(fetched);
        assertEquals(created.getName(), fetched.getName());
    }

    @Test
    void testUpdateEvent() {
        EventDTO event = new EventDTO(null, "Evento Test", "2025-12-10", testVenue.getId().toString());
        EventDTO created = eventUseCase.createDTO(event);
        created.setName("Evento Updated");
        EventDTO updated = eventUseCase.updateDTO(created.getId(), created).orElse(null);
        assertNotNull(updated);
        assertEquals("Evento Updated", updated.getName());
    }

    @Test
    void testDeleteEvent() {
        EventDTO event = new EventDTO(null, "Evento Test", "2025-12-10", testVenue.getId().toString());
        EventDTO created = eventUseCase.createDTO(event);
        boolean deleted = eventUseCase.deleteEvent(created.getId());
        assertTrue(deleted);
        assertTrue(eventUseCase.getByIdDTO(created.getId()).isEmpty());
    }
}
