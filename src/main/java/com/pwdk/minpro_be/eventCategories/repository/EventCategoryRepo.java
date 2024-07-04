package com.pwdk.minpro_be.eventCategories.repository;

import com.pwdk.minpro_be.eventCategories.entity.EventCategories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventCategoryRepo extends JpaRepository<EventCategories,Long> {
    Optional<EventCategories> findByNameIgnoreCase(String name);

}
