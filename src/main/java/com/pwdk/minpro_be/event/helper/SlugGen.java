package com.pwdk.minpro_be.event.helper;

import com.github.slugify.Slugify;
import org.springframework.stereotype.Component;

@Component
public class SlugGen {
    private final Slugify slugify;

    public SlugGen(Slugify slugify) {
        this.slugify = slugify;
    }

    public String slugGenerator(String eventName) {
        return slugify.slugify(eventName);
    }
}
