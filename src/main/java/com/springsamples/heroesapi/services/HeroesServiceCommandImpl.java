package com.springsamples.heroesapi.services;

import com.springsamples.heroesapi.domain.Hero;
import com.springsamples.heroesapi.mappers.IHeroMapperDomainToEntity;
import com.springsamples.heroesapi.repositories.HeroesRepositoryCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HeroesServiceCommandImpl implements HeroesServiceCommand {

    private final HeroesRepositoryCommand repository;
    private final IHeroMapperDomainToEntity mapper;

    @Override
    public void updateHero(Hero hero) {
        int result = repository.update(mapper.map(hero));
        if(result < 1) {
            throw new RuntimeException("Update fails");
        }
    }
}
