package com.pwdk.minpro_be.eventOrganizer.repository;

import com.pwdk.minpro_be.eventOrganizer.entity.EventOrganizer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventOrganizationRepo extends JpaRepository<EventOrganizer, Long> {
    Page<EventOrganizer> findByUserId(Pageable pageable, Long id);
}
