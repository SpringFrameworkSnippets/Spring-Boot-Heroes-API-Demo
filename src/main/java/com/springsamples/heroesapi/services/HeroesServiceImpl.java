package com.springsamples.heroesapi.services;

import com.springsamples.heroesapi.domain.Hero;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class HeroesServiceImpl implements HeroesService {
    //TODO we should get hero entities from some repository and map them out
    @Override
    public List<Hero> findAll() {
        return List.of(
                Hero.builder()
                        .id(UUID.randomUUID())
                        .name("Batman")
                        .build());
    }
}
