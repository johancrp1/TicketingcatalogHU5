package com.example.ticketingcatalog.domain.service;

import com.example.ticketingcatalog.domain.model.VenueModel;
import java.util.List;
import java.util.Optional;

public interface IVenueService {

    List<VenueModel> getAll();
    Optional<VenueModel> getById(Long id);
    VenueModel create(VenueModel venue);
    Optional<VenueModel> update(Long id, VenueModel venue);
    boolean delete(Long id);
}
