package com.springsamples.heroesapi.services;

import com.springsamples.heroesapi.web.model.HeroDto;

import java.util.List;

public interface HeroesFacade {
    List<HeroDto> findAll();
}
