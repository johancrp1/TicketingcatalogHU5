package com.example.ticketingcatalog.infrastructure.adapters.out.jpa.repository;

import com.example.ticketingcatalog.infrastructure.adapters.out.jpa.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEventRepository extends JpaRepository<EventEntity, Long> {
    boolean existsByNameIgnoreCase(String name);
}
