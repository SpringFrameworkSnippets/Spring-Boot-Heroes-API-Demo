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

import java.util.Optional;
import java.util.UUID;

import static com.springsamples.heroesapi.constants.Test.BATMAN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.reset;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = HeroesServiceQueryImpl.class)
public class HeroesServiceQueryImplFindByIdTst {

    private static final UUID ID = UUID.fromString("b34d6c68-d9ee-42ea-aa39-71bc107fbd0b");

    @Autowired
    HeroesServiceQuery service;

    @MockBean
    IHeroMapperEntityToDomain mapper;

    @MockBean
    HeroesRepository repository;

    @BeforeEach
    void beforeEach() {
        given(repository.findById(any())).willReturn(Optional.of(
                HeroEntity.builder()
                        .id(ID)
                        .name(BATMAN)
                        .build()
        ));

        given(mapper.map(any())).willReturn(Hero.builder()
                .id(ID)
                .name("domain")
                .build());
    }

    @AfterEach
    void afterEach() {
        reset(repository);
        reset(mapper);
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
        then(repository).shouldHaveNoInteractions();
        var optionalHero = service.findById(ID);
        then(repository).should(only()).findById(ID);
        then(repository).shouldHaveNoMoreInteractions();
        assertThat(optionalHero.isPresent()).isTrue();
        optionalHero.ifPresent((hero) -> assertThat(hero.getId()).isEqualTo(ID));
    }
}
