package com.pwdk.minpro_be.eventCategories.service;

import com.pwdk.minpro_be.eventCategories.entity.EventCategories;

import java.util.List;

public interface EventCategoriesService {

    List<EventCategories> findAllCategory();
    EventCategories findByName(String name);
}
