package com.springsamples.heroesapi.repositories;

import com.springsamples.heroesapi.repositories.entities.HeroEntity;
import org.springframework.stereotype.Repository;

@Repository
public class HeroesRepositoryCommandImpl implements HeroesRepositoryCommand {
    @Override
    public int update(HeroEntity entity) {
        return 0;
    }
}
