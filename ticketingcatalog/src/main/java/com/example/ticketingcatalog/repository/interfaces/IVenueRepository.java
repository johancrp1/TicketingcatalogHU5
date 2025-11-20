package com.example.ticketingcatalog.repository.interfaces;

import com.example.ticketingcatalog.entity.VenueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVenueRepository extends JpaRepository<VenueEntity, Long> {
    boolean existsByName(String name);
}
