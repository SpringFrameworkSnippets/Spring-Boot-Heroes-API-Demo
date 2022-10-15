package com.springsamples.heroesapi.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class HeroesRepositoryJpaImplTest {

    @Autowired
    private HeroesRepository repository;

    @Test
    void findAll() {
        var heroes = repository.findAll();
        assertThat(heroes).isNotEmpty();
    }
}