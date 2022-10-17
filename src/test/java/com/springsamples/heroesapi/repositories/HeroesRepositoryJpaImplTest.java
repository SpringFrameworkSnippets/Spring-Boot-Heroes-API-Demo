package com.springsamples.heroesapi.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static com.springsamples.heroesapi.constants.Test.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class HeroesRepositoryJpaImplTest {

    @Autowired
    private HeroesRepositoryQuery repository;

    @Test
    @DisplayName("Should retrieve hero entity list")
    void findAll() {
        var heroes = repository.findAll();
        assertThat(heroes).isNotEmpty();
        assertThat(heroes).hasSize(4);
    }

    @Test
    @DisplayName("Should retrieve hero entity by ID")
    void findById() {
        repository.findById(UUID.fromString(BATMAN_VALID_HERO_ID))
                .ifPresent((hero) -> assertThat(hero.getId())
                        .isEqualTo(UUID.fromString(BATMAN_VALID_HERO_ID)));
    }

    @Test
    @DisplayName("Should retrieve hero list filtered by name")
    void findByName() {
        var heroes = repository.findByNameContains(FILTER_PARAM_VALUE);
        assertThat(heroes).isNotEmpty();
        heroes.stream()
                .filter(h -> h.getName().contains(FILTER_PARAM_VALUE))
                .findFirst().ifPresent((hero) -> assertThat(hero.getName()).isEqualTo(BATMAN));
    }
}