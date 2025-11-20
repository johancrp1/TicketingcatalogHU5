package com.example.ticketingcatalog.repository.interfaces;

import com.example.ticketingcatalog.entity.EventEntity;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface IEventRepository extends JpaRepository<EventEntity, Long> {
    boolean existsByNameIgnoreCase(String name);
}




