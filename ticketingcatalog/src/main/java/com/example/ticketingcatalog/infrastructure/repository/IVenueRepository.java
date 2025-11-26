package com.example.ticketingcatalog.infrastructure.repository;

import com.example.ticketingcatalog.infrastructure.entity.VenueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVenueRepository extends JpaRepository<VenueEntity, Long> {
    boolean existsByName(String name);
}
