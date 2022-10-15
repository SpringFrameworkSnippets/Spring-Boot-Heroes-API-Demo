package com.springsamples.heroesapi.repositories;

import com.springsamples.heroesapi.repositories.entities.HeroEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class HeroesRepositoryJpaImpl implements HeroesRepository {
    @Override
    public List<HeroEntity> findAll() {
        return Collections.emptyList();
    }
}
