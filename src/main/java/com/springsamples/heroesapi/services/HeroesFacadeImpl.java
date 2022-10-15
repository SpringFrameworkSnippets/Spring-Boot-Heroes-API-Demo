package com.springsamples.heroesapi.services;

import com.springsamples.heroesapi.web.model.HeroDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HeroesFacadeImpl implements HeroesFacade {
    @Override
    public List<HeroDto> findAll() {
        return List.of(HeroDto.builder()
                .id(UUID.randomUUID())
                .name("Batman")
                .build());
    }
}
