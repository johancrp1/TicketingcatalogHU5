package com.example.ticketingcatalog.domain.ports.in;

import com.example.ticketingcatalog.domain.model.VenueModel;
import java.util.List;
import java.util.Optional;

public interface VenueUseCasePort {

    List<VenueModel> getAllVenues();

    Optional<VenueModel> getVenueById(Long id);

    VenueModel createVenue(VenueModel venue);

    Optional<VenueModel> updateVenue(Long id, VenueModel venue);

    boolean deleteVenue(Long id);
}
