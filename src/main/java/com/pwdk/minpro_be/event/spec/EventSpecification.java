package com.pwdk.minpro_be.event.spec;

import com.pwdk.minpro_be.event.entity.Event;
import com.pwdk.minpro_be.eventCategories.entity.EventCategories;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class EventSpecification {
    public static Specification<Event> hasName(String name) {
        return (event, cq, cb) ->
                name == null ? cb.conjunction() :
                        cb.like(cb.lower(event.get("name")), '%' + name.toLowerCase() + "%");
    }

    public static Specification<Event> hasCategories(String category) {
        return (root, query, cb) -> {
            if (category == null || category.isEmpty()) {
                return cb.conjunction();
            }
            Join<Event, EventCategories> categoryJoin = root.join("eventCategory", JoinType.INNER);
            return cb.like(cb.lower(categoryJoin.get("name")), '%' + category.toLowerCase() + "%");
        };
    }

    public static Specification<Event> hasCities(String city) {
        return (event, cq, cb) ->
                city == null || city.isEmpty() ? cb.conjunction() :
                        cb.like(cb.lower(event.get("city")), '%' + city.toLowerCase() + "%");
    }

    public static Specification<Event> hasDate(LocalDate date) {
        return (event, cq, cb) ->
                date == null ? cb.conjunction() : cb.equal(event.get("date"), date);
    }

    public static Specification<Event> isNotDeleted() {
        return (event, cq, cb) ->
                cb.isNull(event.get("deletedAt"));
    }

    public static Specification<Event> upcomingEvent() {
        return (event, cq, cb) -> {
            LocalDate today = LocalDate.now();
            Date todayDate = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
            return cb.greaterThanOrEqualTo(event.get("date"), todayDate);
        };
    }
}
