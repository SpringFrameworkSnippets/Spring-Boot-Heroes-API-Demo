package com.springsamples.heroesapi.services.facade;

import com.springsamples.heroesapi.domain.Hero;
import com.springsamples.heroesapi.mappers.IHeroMapperDomainToDto;
import com.springsamples.heroesapi.mappers.IHeroMapperDtoToDomain;
import com.springsamples.heroesapi.services.*;
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

import static com.springsamples.heroesapi.constants.Test.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration( classes = {HeroesFacadeImpl.class})
public class HeroesFacadeImplFindByNameTst {

    @Autowired
    private HeroesFacade facade;

    @MockBean
    private HeroesServiceQuery serviceQuery;

    @MockBean
    HeroesServiceCommand serviceCommand;

    @MockBean
    IHeroMapperDtoToDomain dtoToDomain;

    @MockBean
    private IHeroMapperDomainToDto domainToDto;

    @MockBean
    CacheService cacheService;

    @BeforeEach
    void beforeEach() {
        given(serviceQuery.findByNameContains(any())).willReturn(List.of(
                Hero.builder()
                        .id(UUID.randomUUID())
                        .name(BATMAN)
                        .build(),
                Hero.builder()
                        .id(UUID.randomUUID())
                        .name(SUPERMAN)
                        .build()
        ));
        given(domainToDto.map(any())).willReturn(HeroDto.builder()
                .id(UUID.randomUUID())
                .name("dto")
                .build());
    }

    @AfterEach
    void afterEach() {
        reset(serviceQuery);
        reset(domainToDto);
    }

    @Test
    @DisplayName("Should get heroes domain objects by name filter from service")
    void findByName_withServiceInteraction() {
        then(serviceQuery).shouldHaveNoInteractions();
        then(domainToDto).shouldHaveNoInteractions();
        var heroesByName = facade.findByNameContains(FILTER_PARAM_VALUE);
        assertThat(heroesByName).isNotEmpty();
        assertThat(heroesByName).hasSize(2);
        then(domainToDto).should(times(2)).map(any());
        then(domainToDto).shouldHaveNoMoreInteractions();
        then(serviceQuery).should(only()).findByNameContains(FILTER_PARAM_VALUE);
        then(serviceQuery).shouldHaveNoMoreInteractions();
    }
}
