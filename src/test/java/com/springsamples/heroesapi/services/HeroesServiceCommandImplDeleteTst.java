package com.springsamples.heroesapi.services;

import com.springsamples.heroesapi.mappers.IHeroMapperDomainToEntity;
import com.springsamples.heroesapi.repositories.HeroesRepositoryCommand;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.reset;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = HeroesServiceCommandImpl.class)
public class HeroesServiceCommandImplDeleteTst {

    @Autowired
    private HeroesServiceCommand service;

    @MockBean
    HeroesRepositoryCommand repository;

    @MockBean
    IHeroMapperDomainToEntity mapper;

    @BeforeEach
    void beforeEach() {}

    @AfterEach
    void afterEach() {
        reset(repository);
    }

    @Test
    @DisplayName("Should delete hero using repository successfully")
    void deleteHero() {
        given(repository.delete(any())).willReturn(1);
        then(repository).shouldHaveNoInteractions();
        service.deleteHero(UUID.randomUUID());
        then(repository).should(only()).delete(any());
        then(repository).shouldHaveNoMoreInteractions();
    }
}
