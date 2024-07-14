package com.pwdk.minpro_be.eventCategories.service;

import com.pwdk.minpro_be.eventCategories.entity.EventCategories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventCategoriesService {

    Page<EventCategories> findAllCategory(Pageable pageable);
    EventCategories findByName(String name);
}
