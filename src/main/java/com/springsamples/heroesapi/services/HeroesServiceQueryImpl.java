package com.springsamples.heroesapi.services;

import com.springsamples.heroesapi.domain.Hero;
import com.springsamples.heroesapi.mappers.IHeroMapperEntityToDomain;
import com.springsamples.heroesapi.repositories.HeroesRepositoryQuery;
import com.springsamples.heroesapi.repositories.entities.HeroEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HeroesServiceQueryImpl implements HeroesServiceQuery {

    private final HeroesRepositoryQuery repository;
    private final IHeroMapperEntityToDomain mapper;

    @Override
    public List<Hero> findAll() {
        return toDomain(repository.findAll());
    }

    @Override
    public Optional<Hero> findById(UUID id) {
        return repository.findById(id)
                .map(mapping());
    }

    @Override
    public List<Hero> findByNameContains(String name) {
        return toDomain(repository.findByNameContains(name));
    }

    private Function<HeroEntity, Hero> mapping() {
        return mapper::map;
    }

    private List<Hero> toDomain(List<HeroEntity> entities) {
        return entities.stream()
                .map(mapping())
                .collect(Collectors.toList());
    }
}
