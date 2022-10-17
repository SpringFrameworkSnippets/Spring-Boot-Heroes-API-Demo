package com.springsamples.heroesapi.services;

import com.springsamples.heroesapi.domain.Hero;
import com.springsamples.heroesapi.exceptions.HeroNotFoundException;
import com.springsamples.heroesapi.mappers.IHeroMapperDomainToEntity;
import com.springsamples.heroesapi.repositories.HeroesRepositoryCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HeroesServiceCommandImpl implements HeroesServiceCommand {

    private final HeroesRepositoryCommand repository;
    private final IHeroMapperDomainToEntity mapper;

    @Override
    public void updateHero(Hero hero) {
        var result = repository.update(mapper.map(hero));
        checkResult(result, hero.getId());
    }

    @Override
    public void deleteHero(UUID id) {
        var result = repository.delete(id);
        checkResult(result, id);
    }

    private void checkResult(int result, UUID id) {
        if (result < 1) {
            throw new HeroNotFoundException(id);
        }
    }
}
