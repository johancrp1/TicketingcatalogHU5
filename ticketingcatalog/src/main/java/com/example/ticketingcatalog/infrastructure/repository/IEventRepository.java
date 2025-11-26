package com.example.ticketingcatalog.infrastructure.repository;

import com.example.ticketingcatalog.infrastructure.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEventRepository extends JpaRepository<EventEntity, Long> {
    boolean existsByNameIgnoreCase(String name);
}
