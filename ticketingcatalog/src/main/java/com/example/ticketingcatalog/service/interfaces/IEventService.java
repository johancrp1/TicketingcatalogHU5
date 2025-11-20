package com.example.ticketingcatalog.service.interfaces;

import com.example.ticketingcatalog.dto.EventDTO;
import com.example.ticketingcatalog.entity.EventEntity;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IEventService {

    List<EventDTO> getAll();

    Optional<EventDTO> getById(Long id);

    EventDTO create(EventDTO event);

    Optional<EventDTO> update(Long id, EventDTO event);

    boolean delete(Long id);



}
