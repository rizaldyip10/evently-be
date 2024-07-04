package com.pwdk.minpro_be.event.repository;

import com.pwdk.minpro_be.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

}
