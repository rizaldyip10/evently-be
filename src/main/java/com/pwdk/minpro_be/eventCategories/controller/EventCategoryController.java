package com.pwdk.minpro_be.eventCategories.controller;

import com.pwdk.minpro_be.eventCategories.service.EventCategoriesService;
import com.pwdk.minpro_be.responses.Response;
import lombok.extern.java.Log;
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
    public ResponseEntity<?> findAllCategory(){
        return Response.success("Categories" , eventCategoriesService.findAllCategory());
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getCategoryByName(@PathVariable String name){
        return Response.success("Category name" , eventCategoriesService.findByName(name));
    }


}
