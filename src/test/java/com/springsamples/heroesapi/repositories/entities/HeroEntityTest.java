package com.springsamples.heroesapi.repositories.entities;

import com.springsamples.heroesapi.web.model.HeroDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class HeroEntityTest {

    @Test
    @DisplayName("Entity should have id and name attrs")
    void heroEntityHasProperties() {
        var id = UUID.randomUUID();
        var name = "Batman";
        var entity = new HeroEntity(id, name);
        assertAll("Has attributes",
                () -> assertEquals(id, entity.getId()),
                () -> assertEquals(name, entity.getName()));
    }
}