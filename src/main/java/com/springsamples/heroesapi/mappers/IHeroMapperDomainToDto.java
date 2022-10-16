package com.springsamples.heroesapi.mappers;

import com.springsamples.heroesapi.domain.Hero;
import com.springsamples.heroesapi.web.model.HeroDto;

public interface IHeroMapperDomainToDto {
    HeroDto map(Hero domain);
}
