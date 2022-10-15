package com.springsamples.heroesapi.mappers;

import com.springsamples.heroesapi.domain.Hero;
import com.springsamples.heroesapi.web.model.HeroDto;
import org.mapstruct.Mapper;

@Mapper
public interface HeroesMapper {
    HeroDto domainToDto(Hero domain);
}
