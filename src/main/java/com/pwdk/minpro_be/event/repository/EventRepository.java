package com.pwdk.minpro_be.event.repository;

import com.pwdk.minpro_be.event.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    Optional<Event> findBySlug(String eventSlug);

    @Query("SELECT DISTINCT e.city FROM Event e")
    Page<String> findAllDistinctCities(Pageable pageable);
}
