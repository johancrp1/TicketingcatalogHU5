package com.example.ticketingcatalog.infrastructure.adapters.out.jpa.repository;

import com.example.ticketingcatalog.infrastructure.adapters.out.jpa.entity.VenueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVenueRepository extends JpaRepository<VenueEntity, Long> {
    boolean existsByName(String name);
}
