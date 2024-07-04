package com.pwdk.minpro_be.eventCategories.service.Impl;

import com.pwdk.minpro_be.eventCategories.entity.EventCategories;
import com.pwdk.minpro_be.eventCategories.repository.EventCategoryRepo;
import com.pwdk.minpro_be.eventCategories.service.EventCategoriesService;
import com.pwdk.minpro_be.exception.ApplicationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventCategoryImpl implements EventCategoriesService {


    private final EventCategoryRepo eventCategoryRepo;

    public EventCategoryImpl(EventCategoryRepo eventCategoryRepo){
        this.eventCategoryRepo = eventCategoryRepo;
    }


    @Override
    public List<EventCategories> findAllCategory() {
        return eventCategoryRepo.findAll();
    }

    @Override
    public EventCategories findByName(String name) {
        Optional<EventCategories> category = eventCategoryRepo.findByNameIgnoreCase(name);
        if (category.isEmpty()) {

            throw new ApplicationException("Category not found");
        }
        return category.get();
    }
}


