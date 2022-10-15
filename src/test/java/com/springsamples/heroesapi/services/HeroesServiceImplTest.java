package com.springsamples.heroesapi.services;

import com.springsamples.heroesapi.domain.Hero;
import com.springsamples.heroesapi.mappers.HeroesMapper;
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
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = HeroesServiceImpl.class)
class HeroesServiceImplTest {

    @Autowired
    HeroesService service;

    @MockBean
    HeroesMapper mapper;

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

        given(mapper.entityToDomain(any())).willReturn(Hero.builder()
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
        then(mapper).should(times(2)).entityToDomain(any());
    }
}