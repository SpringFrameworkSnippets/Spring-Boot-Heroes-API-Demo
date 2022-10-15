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

    private static final String NAME = "Batman";

    @Autowired
    private HeroesRepository repository;

    @Autowired
    TestEntityManager testEntityManager;

    @BeforeEach
    void beforeEach() {
        testEntityManager.persistAndFlush(
                HeroEntity.builder()
                        .name(NAME)
                        .build());
    }

    @Test
    @DisplayName("Should retrieve persisted hero entity")
    void findAll() {
        var heroes = repository.findAll();
        assertThat(heroes).isNotEmpty();
        heroes.stream().findFirst().ifPresentOrElse((hero) ->
                        assertThat(hero.getName()).isEqualTo(NAME),
                RuntimeException::new);
    }
}