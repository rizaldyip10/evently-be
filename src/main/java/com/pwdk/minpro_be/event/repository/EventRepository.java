package com.pwdk.minpro_be.event.repository;

import com.pwdk.minpro_be.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findBySlug(String eventSlug);
    @Query("SELECT e FROM Event e WHERE e.deletedAt IS NULL")
    List<Event> findAllAndDeletedAtIsNull();
}
