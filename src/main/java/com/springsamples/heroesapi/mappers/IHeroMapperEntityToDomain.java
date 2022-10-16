package com.springsamples.heroesapi.mappers;

import com.springsamples.heroesapi.domain.Hero;
import com.springsamples.heroesapi.repositories.entities.HeroEntity;

public interface IHeroMapperEntityToDomain {
    Hero map(HeroEntity entity);
}
