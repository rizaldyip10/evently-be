package com.pwdk.minpro_be.eventOrganizer.repository;

import com.pwdk.minpro_be.eventOrganizer.entity.EventOrganizer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventOrganizationRepo extends JpaRepository<EventOrganizer, Long> {
}
