package com.example.ticketingcatalog.domain.repository;

import com.example.ticketingcatalog.domain.model.VenueModel;
import java.util.List;
import java.util.Optional;

public interface VenueRepository {
    boolean existsByName(String name);
    Optional<VenueModel> findById(Long id);
    List<VenueModel> findAll();
    VenueModel save(VenueModel venue);
    void deleteById(Long id);
}
