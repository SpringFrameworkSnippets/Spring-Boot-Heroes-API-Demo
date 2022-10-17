package com.springsamples.heroesapi.services.facade;

import com.springsamples.heroesapi.domain.Hero;
import com.springsamples.heroesapi.mappers.IHeroMapperDomainToDto;
import com.springsamples.heroesapi.mappers.IHeroMapperDtoToDomain;
import com.springsamples.heroesapi.services.*;
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
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration( classes = {HeroesFacadeImpl.class})
public class HeroesFacadeImplDeleteHeroTst {

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

    @MockBean
    CacheService cacheService;

    @BeforeEach
    void beforeEach() {
        doNothing().when(serviceCommand).deleteHero(any());
        given(dtoToDomain.map(any())).willReturn(Hero.builder()
                .id(UUID.randomUUID())
                .name("dto")
                .build());
        doNothing().when(cacheService).invalidate(anyList());
    }

    @AfterEach
    void afterEach() {
        reset(serviceCommand);
        reset(dtoToDomain);
    }

    @Test
    @DisplayName("Should delete hero using service")
    void deleteHero_withServiceInteraction() {
        then(serviceCommand).shouldHaveNoInteractions();
        then(dtoToDomain).shouldHaveNoInteractions();
        then(cacheService).shouldHaveNoInteractions();
        facade.deleteHero(UUID.randomUUID());
        then(serviceCommand).should(only()).deleteHero(any());
        then(serviceCommand).shouldHaveNoMoreInteractions();
        then(dtoToDomain).should(only()).map(any());
        then(dtoToDomain).shouldHaveNoMoreInteractions();
        // We could validate cache invalidation interaction
        // when delete operation occurs on a separate test.
        then(cacheService).should(only()).invalidate(anyList());
        then(cacheService).shouldHaveNoMoreInteractions();
    }
}
