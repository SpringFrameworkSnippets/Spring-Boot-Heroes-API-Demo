package com.springsamples.heroesapi.services;

import com.springsamples.heroesapi.web.model.HeroDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HeroesFacade {
    List<HeroDto> findAll();
    Optional<HeroDto> findById(UUID id);
}
