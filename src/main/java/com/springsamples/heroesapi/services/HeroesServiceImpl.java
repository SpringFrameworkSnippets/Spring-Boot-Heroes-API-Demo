package com.springsamples.heroesapi.services;

import com.springsamples.heroesapi.domain.Hero;
import com.springsamples.heroesapi.mappers.HeroesMapper;
import com.springsamples.heroesapi.repositories.HeroesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HeroesServiceImpl implements HeroesService {

    private final HeroesRepository repository;
    private final HeroesMapper mapper;
    @Override
    public List<Hero> findAll() {
        return repository.findAll().stream()
                .map(mapper::entityToDomain)
                .collect(Collectors.toList());
    }
}
