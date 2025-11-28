package com.example.ticketingcatalog.domain.ports.in;

import com.example.ticketingcatalog.domain.model.EventModel;
import java.util.List;
import java.util.Optional;

public interface EventUseCasePort {

    List<EventModel> getAllEvents();

    Optional<EventModel> getEventById(Long id);

    EventModel createEvent(EventModel event);

    Optional<EventModel> updateEvent(Long id, EventModel event);

    boolean deleteEvent(Long id);
}
