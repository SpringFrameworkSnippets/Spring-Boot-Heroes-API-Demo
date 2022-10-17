package com.springsamples.heroesapi.repositories;

import com.springsamples.heroesapi.repositories.entities.HeroEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.UUID;

import static com.springsamples.heroesapi.constants.Test.VALID_HERO_ID;
import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@ContextConfiguration( classes = HeroesRepositoryCommandImpl.class)
class HeroesRepositoryCommandImplTest {

    @Autowired
    HeroesRepositoryCommand repository;

    private HeroEntity entity;

    @BeforeEach
    void beforeEach() {
        entity = HeroEntity.builder()
                .id(UUID.fromString(VALID_HERO_ID))
                .name("Robin")
                .build();
    }

    @Test
    void update() {
        int result = repository.update(entity);
        assertThat(result).isGreaterThan(0);
    }
}