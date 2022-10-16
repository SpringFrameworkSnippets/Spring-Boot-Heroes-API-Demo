package com.springsamples.heroesapi.services;

import com.springsamples.heroesapi.domain.Hero;
import com.springsamples.heroesapi.mappers.IHeroMapperEntityToDomain;
import com.springsamples.heroesapi.repositories.HeroesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HeroesServiceImpl implements HeroesService {

    private final HeroesRepository repository;
    private final IHeroMapperEntityToDomain mapper;

    @Override
    public List<Hero> findAll() {
        return repository.findAll().stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Hero> findById(UUID id) {
        return repository.findById(id)
                .map(mapper::map);
    }

    @Override
    public List<Hero> findByNameContains(String name) {
        return Collections.emptyList();
    }
}
