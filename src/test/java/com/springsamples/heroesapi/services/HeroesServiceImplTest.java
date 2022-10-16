package com.springsamples.heroesapi.services;

import com.springsamples.heroesapi.domain.Hero;
import com.springsamples.heroesapi.mappers.IHeroMapperEntityToDomain;
import com.springsamples.heroesapi.repositories.HeroesRepository;
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
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = HeroesServiceImpl.class)
class HeroesServiceImplTest {

    private static final UUID ID = UUID.fromString("b34d6c68-d9ee-42ea-aa39-71bc107fbd0b");

    @Autowired
    HeroesService service;

    @MockBean
    IHeroMapperEntityToDomain mapper;

    @MockBean
    HeroesRepository repository;

    @BeforeEach
    void beforeEach() {
        given(repository.findAll()).willReturn(List.of(
                HeroEntity.builder()
                        .id(UUID.randomUUID())
                        .name("Batman")
                        .build(),
                HeroEntity.builder()
                        .id(UUID.randomUUID())
                        .name("Superman")
                        .build()
        ));

        given(repository.findById(any())).willReturn(Optional.of(
                HeroEntity.builder()
                        .id(ID)
                        .name("Tracer")
                        .build()
        ));

        given(mapper.map(any())).willReturn(Hero.builder()
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
    @DisplayName("Should return hero domain objects")
    void findAll() {
        var heroes = service.findAll();
        assertThat(heroes).isNotEmpty();
    }

    @Test
    @DisplayName("Should get hero entities from repository")
    void findAll_withRepositoryInteraction() {
        then(repository).shouldHaveNoInteractions();
        then(mapper).shouldHaveNoInteractions();
        var heroes = service.findAll();
        assertThat(heroes).isNotEmpty();
        assertThat(heroes).hasSize(2);
        then(repository).should(only()).findAll();
        then(mapper).should(times(2)).map(any());
    }

    @Test
    @DisplayName("Should return hero domain object")
    void findById_notEmpty() {
        var optionalHero = service.findById(ID);
        assertThat(optionalHero.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should get hero entity from repository by ID")
    void findById_notEmpty_withRepoInteraction() {
        given(mapper.map(any())).willReturn(
                Hero.builder()
                        .id(ID)
                        .name("Reaper")
                        .build());
        then(repository).shouldHaveNoInteractions();
        var optionalHero = service.findById(ID);
        then(repository).should(only()).findById(ID);
        then(repository).shouldHaveNoMoreInteractions();
        assertThat(optionalHero.isPresent()).isTrue();
        optionalHero.ifPresent((hero) -> assertThat(hero.getId()).isEqualTo(ID));
    }
}