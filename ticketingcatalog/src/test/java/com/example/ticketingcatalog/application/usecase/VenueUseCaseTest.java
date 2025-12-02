package com.example.ticketingcatalog.application.usecase;

import com.example.ticketingcatalog.application.dto.EventDTO;
import com.example.ticketingcatalog.application.dto.VenueDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class VenueUseCaseTest {

    @Autowired
    private VenueUseCase venueUseCase;

    private VenueDTO testVenue;

    @BeforeEach
    void setup() {
        // Crear un Venue con lista de eventos vacía por ahora
        testVenue = new VenueDTO(null, "Venue Test", "Bogotá", null);
    }

    @Test
    void testCreateVenue() {
        VenueDTO created = venueUseCase.createDTO(testVenue);
        assertNotNull(created.getId());
        assertEquals("Venue Test", created.getName());
        assertEquals("Bogotá", created.getLocation());
    }

    @Test
    void testGetVenueById() {
        VenueDTO created = venueUseCase.createDTO(testVenue);
        VenueDTO fetched = venueUseCase.getByIdDTO(created.getId()).orElse(null);
        assertNotNull(fetched);
        assertEquals(created.getName(), fetched.getName());
    }

    @Test
    void testUpdateVenue() {
        VenueDTO created = venueUseCase.createDTO(testVenue);
        created.setName("Venue Updated");
        VenueDTO updated = venueUseCase.updateDTO(created.getId(), created).orElse(null);
        assertNotNull(updated);
        assertEquals("Venue Updated", updated.getName());
    }

    @Test
    void testDeleteVenue() {
        VenueDTO created = venueUseCase.createDTO(testVenue);
        boolean deleted = venueUseCase.deleteVenue(created.getId());
        assertTrue(deleted);
        assertTrue(venueUseCase.getByIdDTO(created.getId()).isEmpty());
    }

    @Test
    void testVenueWithEvents() {
        // Crear eventos asociados al venue
        EventDTO event1 = new EventDTO(null, "Evento 1", "2025-12-10", null);
        EventDTO event2 = new EventDTO(null, "Evento 2", "2025-12-15", null);
        testVenue.setEvents(List.of(event1, event2));

        VenueDTO created = venueUseCase.createDTO(testVenue);
        assertNotNull(created.getId());
        assertEquals(2, created.getEvents().size());
    }
}
