package com.springsamples.heroesapi.repositories;

import com.springsamples.heroesapi.repositories.entities.HeroEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HeroesRepositoryJpaImpl extends JpaRepository<HeroEntity, UUID> ,HeroesRepository { }
