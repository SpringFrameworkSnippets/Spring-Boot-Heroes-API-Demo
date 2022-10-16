package com.springsamples.heroesapi.exceptions;

import java.util.UUID;

public class HeroNotFoundException extends RuntimeException {
    public HeroNotFoundException(UUID id) {
        super(buildMessage(id));
    }

    private static String buildMessage(UUID id) {
        return String.format("Hero with id %s not found", id);
    }
}
