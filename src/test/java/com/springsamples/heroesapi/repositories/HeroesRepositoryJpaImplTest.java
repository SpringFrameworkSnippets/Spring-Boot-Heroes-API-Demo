package com.springsamples.heroesapi.repositories;

import com.springsamples.heroesapi.repositories.entities.HeroEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties =
        {"spring.flyway.enabled=false",
                "spring.jpa.hibernate.ddl-auto=create"})
class HeroesRepositoryJpaImplTest {

    private static final String BATMAN = "Batman";
    private static final String SOLDIER = "Soldier";
    private static final String FILTER_NAME = "man";

    @Autowired
    private HeroesRepository repository;

    @Autowired
    TestEntityManager testEntityManager;

    private HeroEntity savedHeroEntity;

    @BeforeEach
    void beforeEach() {
        savedHeroEntity = testEntityManager.persistAndFlush(
                HeroEntity.builder()
                        .name(BATMAN)
                        .build());

        testEntityManager.persistAndFlush(
                HeroEntity.builder()
                        .name(SOLDIER)
                        .build());
    }

    @Test
    @DisplayName("Should retrieve hero entity list")
    void findAll() {
        var heroes = repository.findAll();
        assertThat(heroes).isNotEmpty();
        assertThat(heroes).hasSize(2);
    }

    @Test
    @DisplayName("Should retrieve hero entity by ID")
    void findById() {
        repository.findById(savedHeroEntity.getId())
                .ifPresent((hero) -> assertThat(hero.getId())
                        .isEqualTo(savedHeroEntity.getId()));
    }

    @Test
    @DisplayName("Should retrieve hero list filtered by name")
    void findByName() {
        var heroes = repository.findByNameContains(FILTER_NAME);
        assertThat(heroes).isNotEmpty();
        heroes.stream()
                .filter(h -> h.getName().contains(FILTER_NAME))
                .findFirst().ifPresent((hero) -> assertThat(hero.getName()).isEqualTo(BATMAN));
    }
}