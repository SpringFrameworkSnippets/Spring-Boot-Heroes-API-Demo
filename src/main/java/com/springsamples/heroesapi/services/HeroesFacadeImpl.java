package com.springsamples.heroesapi.services;

import com.springsamples.heroesapi.mappers.IHeroMapperDomainToDto;
import com.springsamples.heroesapi.mappers.IHeroMapperDtoToDomain;
import com.springsamples.heroesapi.web.model.HeroDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HeroesFacadeImpl implements HeroesFacade {

    private final HeroesService service;
    private final IHeroMapperDomainToDto domainToDto;
    private final IHeroMapperDtoToDomain dtoToDomain;

    @Cacheable(cacheNames = "heroesCache")
    @Override
    public List<HeroDto> findAll() {
        return service.findAll().stream()
                .map(domainToDto::map)
                .collect(Collectors.toList());
    }

    @Cacheable(cacheNames = "heroCache", key = "#id")
    @Override
    public Optional<HeroDto> findById(UUID id) {
        return service.findById(id).map(domainToDto::map);
    }

    @Cacheable(cacheNames = "heroByNameCache", key = "#name")
    @Override
    public List<HeroDto> findByNameContains(String name) {
        return service.findByNameContains(name).stream()
                .map(domainToDto::map)
                .collect(Collectors.toList());
    }

    @Override
    public void updateHero(HeroDto dto) {
        service.updateHero(dtoToDomain.map(dto));
    }
}
