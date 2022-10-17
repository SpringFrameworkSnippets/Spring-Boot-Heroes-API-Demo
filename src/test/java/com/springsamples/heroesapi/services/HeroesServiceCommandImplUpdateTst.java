package com.springsamples.heroesapi.services;

import com.springsamples.heroesapi.domain.Hero;
import com.springsamples.heroesapi.mappers.IHeroMapperDomainToEntity;
import com.springsamples.heroesapi.repositories.HeroesRepositoryCommand;
import com.springsamples.heroesapi.repositories.entities.HeroEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = HeroesServiceCommandImpl.class)
class HeroesServiceCommandImplUpdateTst {

    @Autowired
    private HeroesServiceCommand service;

    @MockBean
    IHeroMapperDomainToEntity mapper;

    @MockBean
    HeroesRepositoryCommand repository;

    @BeforeEach
    void beforeEach() {
        given(mapper.map(any())).willReturn(HeroEntity.builder()
                .id(UUID.randomUUID())
                .name("domain")
                .build());
    }

    @AfterEach
    void afterEach() {
        reset(repository);
        reset(mapper);
    }

    @Test
    @DisplayName("Should update hero using repository successfully")
    void updateHero() {
        given(repository.update(any())).willReturn(1);
        then(repository).shouldHaveNoInteractions();
        then(mapper).shouldHaveNoInteractions();
        service.updateHero(Hero.builder().build());
        then(repository).should(only()).update(any());
        then(repository).shouldHaveNoMoreInteractions();
        then(mapper).should(only()).map(any());
        then(mapper).shouldHaveNoMoreInteractions();
    }

    @Test
    @DisplayName("Should raise exception when update using repository fails")
    void updateHero_repositoryFailsToUpdate() {
        given(repository.update(any())).willReturn(0);
        then(repository).shouldHaveNoInteractions();
        then(mapper).shouldHaveNoInteractions();
        assertThrows(RuntimeException.class, () -> service.updateHero(Hero.builder().build()));
        then(repository).should(only()).update(any());
        then(repository).shouldHaveNoMoreInteractions();
        then(mapper).should(only()).map(any());
        then(mapper).shouldHaveNoMoreInteractions();
    }

}