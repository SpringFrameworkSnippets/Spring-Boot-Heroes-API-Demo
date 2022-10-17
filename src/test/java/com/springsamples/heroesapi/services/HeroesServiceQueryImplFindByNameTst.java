package com.springsamples.heroesapi.services;

import com.springsamples.heroesapi.domain.Hero;
import com.springsamples.heroesapi.mappers.IHeroMapperEntityToDomain;
import com.springsamples.heroesapi.repositories.HeroesRepositoryQuery;
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

import java.util.List;
import java.util.UUID;

import static com.springsamples.heroesapi.constants.Test.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = HeroesServiceQueryImpl.class)
public class HeroesServiceQueryImplFindByNameTst {

    @Autowired
    HeroesServiceQuery service;

    @MockBean
    IHeroMapperEntityToDomain mapper;

    @MockBean
    HeroesRepositoryQuery repository;

    @BeforeEach
    void beforeEach() {
        given(repository.findByNameContains(any())).willReturn(List.of(
                HeroEntity.builder()
                        .id(UUID.randomUUID())
                        .name(BATMAN)
                        .build(),
                HeroEntity.builder()
                        .id(UUID.randomUUID())
                        .name(SUPERMAN)
                        .build()
        ));

        given(mapper.map(any())).willReturn(Hero.builder()
                .id(UUID.randomUUID())
                .name(FILTER_PARAM_VALUE)
                .build());
    }

    @AfterEach
    void afterEach() {
        reset(repository);
        reset(mapper);
    }

    @Test
    @DisplayName("Should get hero list from repository by name filter")
    void findByName_withRepoInteraction() {
        then(repository).shouldHaveNoMoreInteractions();
        then(mapper).shouldHaveNoMoreInteractions();
        var heroesByName = service.findByNameContains(FILTER_PARAM_VALUE);
        then(repository).should(only()).findByNameContains(FILTER_PARAM_VALUE);
        then(repository).shouldHaveNoMoreInteractions();
        then(mapper).should(times(2)).map(any());
        assertThat(heroesByName).isNotEmpty();
        assertThat(heroesByName.stream()
                .filter(h -> h.getName().contains(FILTER_PARAM_VALUE))
                .count())
                .isEqualTo(2);
    }
}
