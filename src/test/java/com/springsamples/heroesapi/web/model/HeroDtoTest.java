package com.springsamples.heroesapi.web.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeroDtoTest {

    @Test
    @DisplayName("Dto should have id and name attrs")
    void heroDtoHasProperties() {
        var id = UUID.randomUUID();
        var name = "Batman";
        var dto = new HeroDto(id, name);
        assertAll("Has attributes",
                () -> assertEquals(id, dto.getId()),
                () -> assertEquals(name, dto.getName()));
    }
}
