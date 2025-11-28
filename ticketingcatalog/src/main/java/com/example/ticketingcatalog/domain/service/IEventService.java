package com.example.ticketingcatalog.domain.service;

import com.example.ticketingcatalog.domain.model.EventModel;
import java.util.List;
import java.util.Optional;

public interface IEventService {

    List<EventModel> getAll();
    Optional<EventModel> getById(Long id);
    EventModel create(EventModel event);
    Optional<EventModel> update(Long id, EventModel event);
    boolean delete(Long id);
}
