package com.springsamples.heroesapi.mappers;

import com.springsamples.heroesapi.domain.Hero;
import com.springsamples.heroesapi.repositories.entities.HeroEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HeroesMapperImpl.class})
public class HeroesMapperTest {

    private static final UUID ID = UUID.randomUUID();
    private static final String NAME = "Batman";

    @Autowired
    HeroesMapper mapper;

    private Hero domain;
    private HeroEntity entity;

    @BeforeEach
    void beforeEach() {
        domain = Hero.builder()
                .id(ID)
                .name(NAME)
                .build();
        entity = HeroEntity.builder()
                .id(ID)
                .name(NAME)
                .build();
    }

    @Test
    @DisplayName("Should map domain hero object to presentational one")
    void map_domainToDto() {
        var dto = mapper.domainToDto(domain);
        assertThat(dto.getId()).isEqualTo(domain.getId());
        assertThat(dto.getName()).isEqualTo(domain.getName());
    }

    @Test
    @DisplayName("Should map entity hero object to domain one")
    void map_entityToDomain() {
        var domain = mapper.entityToDomain(entity);
        assertThat(domain.getId()).isEqualTo(entity.getId());
        assertThat(domain.getName()).isEqualTo(entity.getName());
    }
}
