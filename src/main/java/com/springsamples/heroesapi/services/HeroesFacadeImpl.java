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

    private final HeroesServiceQuery serviceQuery;
    private final HeroesServiceCommand serviceCommand;
    private final CacheService cacheService;
    private final IHeroMapperDomainToDto domainToDto;
    private final IHeroMapperDtoToDomain dtoToDomain;

    @Cacheable(cacheNames = "heroesCache")
    @Override
    public List<HeroDto> findAll() {
        return serviceQuery.findAll().stream()
                .map(domainToDto::map)
                .collect(Collectors.toList());
    }

    @Cacheable(cacheNames = "heroCache", key = "#id")
    @Override
    public Optional<HeroDto> findById(UUID id) {
        return serviceQuery.findById(id).map(domainToDto::map);
    }

    @Cacheable(cacheNames = "heroByNameCache", key = "#name")
    @Override
    public List<HeroDto> findByNameContains(String name) {
        return serviceQuery.findByNameContains(name).stream()
                .map(domainToDto::map)
                .collect(Collectors.toList());
    }

    @Override
    public void updateHero(HeroDto dto) {
        serviceCommand.updateHero(dtoToDomain.map(dto));
        cacheService.invalidate(List.of("heroesCache", "heroCache", "heroByNameCache"));
    }

    @Override
    public void deleteHero(UUID id) {}
}
