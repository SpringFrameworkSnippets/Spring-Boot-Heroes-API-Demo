package com.springsamples.heroesapi.services.facade;

import com.springsamples.heroesapi.domain.Hero;
import com.springsamples.heroesapi.mappers.IHeroMapperDomainToDto;
import com.springsamples.heroesapi.mappers.IHeroMapperDtoToDomain;
import com.springsamples.heroesapi.services.HeroesFacade;
import com.springsamples.heroesapi.services.HeroesFacadeImpl;
import com.springsamples.heroesapi.services.HeroesServiceCommand;
import com.springsamples.heroesapi.services.HeroesServiceQuery;
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

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration( classes = {HeroesFacadeImpl.class})
public class HeroesFacadeImplUpdateHeroTst {

    @Autowired
    private HeroesFacade facade;

    @MockBean
    private HeroesServiceCommand serviceCommand;

    @MockBean
    private HeroesServiceQuery serviceQuery;

    @MockBean
    private IHeroMapperDtoToDomain dtoToDomain;

    @MockBean
    private IHeroMapperDomainToDto domainToDto;

    @BeforeEach
    void beforeEach() {
        doNothing().when(serviceCommand).updateHero(any());
        given(dtoToDomain.map(any())).willReturn(Hero.builder()
                .id(UUID.randomUUID())
                .name("dto")
                .build());
    }

    @AfterEach
    void afterEach() {
        reset(serviceCommand);
        reset(dtoToDomain);
    }

    @Test
    @DisplayName("Should update hero using service")
    void updateHero_withServiceInteraction() {
        then(serviceCommand).shouldHaveNoInteractions();
        then(dtoToDomain).shouldHaveNoInteractions();
        facade.updateHero(HeroDto.builder().build());
        then(serviceCommand).should(only()).updateHero(any());
        then(serviceCommand).shouldHaveNoMoreInteractions();
        then(dtoToDomain).should(only()).map(any());
        then(dtoToDomain).shouldHaveNoMoreInteractions();
    }
}
