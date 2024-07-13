package com.pwdk.minpro_be.event.spec;

import com.pwdk.minpro_be.event.entity.Event;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.List;

public class EventSpecification {
    public static Specification<Event> hasName(String name) {
        return (event, cq, cb) ->
                name == null ? cb.conjunction() :
                        cb.like(cb.lower(event.get("name")), '%' + name.toLowerCase() + "%");
    }

    public static Specification<Event> hasCategories(String category) {
        return (event, cq, cb) ->
                category == null || category.isEmpty() ? cb.conjunction() :
                        cb.like(cb.lower(event.get("eventCategory").get("name")), '%' + category.toLowerCase() + "%");
    }

    public static Specification<Event> hasCities(String city) {
        return (event, cq, cb) ->
                city == null || city.isEmpty() ? cb.conjunction() :
                        cb.like(cb.lower(event.get("city")), '%' + city.toLowerCase() + "%");
    }

    public static Specification<Event> hasDate(Date date) {
        return (event, cq, cb) ->
                date == null ? cb.conjunction() : cb.equal(event.get("date"), date);
    }

    public static Specification<Event> isNotDeleted() {
        return (event, cq, cb) ->
                cb.isNull(event.get("deletedAt"));
    }
}
