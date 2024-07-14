package com.pwdk.minpro_be.eventCategories.controller;

import com.pwdk.minpro_be.eventCategories.entity.EventCategories;
import com.pwdk.minpro_be.eventCategories.service.EventCategoriesService;
import com.pwdk.minpro_be.responses.Response;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
@Validated
@Log
public class EventCategoryController {

    private final EventCategoriesService eventCategoriesService;

    public EventCategoryController (EventCategoriesService eventCategoriesService){
        this.eventCategoriesService = eventCategoriesService;
    }

    @GetMapping
    public ResponseEntity<?> findAllCategory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<EventCategories> categories = eventCategoriesService.findAllCategory(pageable);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getCategoryByName(@PathVariable String name){
        return Response.success("Category name" , eventCategoriesService.findByName(name));
    }


}
