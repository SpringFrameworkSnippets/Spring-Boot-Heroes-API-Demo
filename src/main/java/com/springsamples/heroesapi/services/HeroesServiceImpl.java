package com.springsamples.heroesapi.services;

import com.springsamples.heroesapi.domain.Hero;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class HeroesServiceImpl implements HeroesService {
    //TODO we should get hero entities from some repository and map them out
    @Override
    public List<Hero> findAll() {
        return Collections.emptyList();
    }
}
