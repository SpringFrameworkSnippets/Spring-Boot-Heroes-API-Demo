package com.springsamples.heroesapi.services;

import com.springsamples.heroesapi.domain.Hero;
import com.springsamples.heroesapi.mappers.HeroesMapper;
import com.springsamples.heroesapi.web.model.HeroDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
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
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.reset;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration( classes = {HeroesFacadeImpl.class})
class HeroesFacadeImplTest {

    @Autowired
    private HeroesFacade facade;

    @MockBean
    private HeroesService service;

    @MockBean
    private HeroesMapper mapper;

    @BeforeEach
    void beforeEach() {
        given(service.findAll()).willReturn(List.of(
                Hero.builder()
                        .id(UUID.randomUUID())
                        .name("Batman")
                        .build(),
                Hero.builder()
                        .id(UUID.randomUUID())
                        .name("Superman")
                        .build()
        ));
        // Here we are not testing mapping logic,
        // so we don't care about the outcome of this computation
        given(mapper.domainToDto(any())).willReturn(HeroDto.builder()
                .id(UUID.randomUUID())
                .name("dto")
                .build());
    }

    @AfterEach
    void afterEach() {
        reset(service);
        reset(mapper);
    }

    @Test
    void findAll() {
        // This is an example of a bad tst
        // At this point, we have provided a findAll implementation for our facade that was not cover
        // by tests upfront. This was the result of a misleading refactor
        var heroes = facade.findAll();
        assertThat(heroes).isNotNull();
    }

    @Test
    @DisplayName("Should get hero domain objects from service")
    void findAll_withServiceInteraction() {
        then(service).shouldHaveNoInteractions();
        var heroes = facade.findAll();
        then(service).should(only()).findAll();
        assertThat(heroes).isNotEmpty();
        assertThat(heroes).hasSize(2);
    }
}