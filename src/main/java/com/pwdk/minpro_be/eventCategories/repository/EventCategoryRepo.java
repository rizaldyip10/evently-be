package com.pwdk.minpro_be.eventCategories.repository;

import com.pwdk.minpro_be.eventCategories.entity.EventCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventCategoryRepo extends JpaRepository<EventCategories,Long> {
    Optional<EventCategories> findByNameIgnoreCase(String name);

}
