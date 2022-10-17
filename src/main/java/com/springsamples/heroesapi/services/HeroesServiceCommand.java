package com.springsamples.heroesapi.services;

import com.springsamples.heroesapi.domain.Hero;

import java.util.UUID;

public interface HeroesServiceCommand {
    void updateHero(Hero domain);
    void deleteHero(UUID id);
}
