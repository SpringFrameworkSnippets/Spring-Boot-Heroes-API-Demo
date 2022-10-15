package com.springsamples.heroesapi.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeroTest {

    @Test
    @DisplayName("domain object should have id and name attrs")
    void heroHasProperties() {
        var id = UUID.randomUUID();
        var name = "Batman";
        var domain = new Hero(id, name);
        assertAll("Has attributes",
                () -> assertEquals(id, domain.getId()),
                () -> assertEquals(name, domain.getName()));
    }
}
