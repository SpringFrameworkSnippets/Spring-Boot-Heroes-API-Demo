package com.springsamples.heroesapi.services;

import com.springsamples.heroesapi.mappers.HeroesMapper;
import com.springsamples.heroesapi.web.model.HeroDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HeroesFacadeImpl implements HeroesFacade {

    private final HeroesService service;
    private final HeroesMapper mapper;

    @Cacheable(cacheNames = "heroesCache")
    @Override
    public List<HeroDto> findAll() {
        return service.findAll().stream()
                .map(mapper::domainToDto)
                .collect(Collectors.toList());
    }
}
