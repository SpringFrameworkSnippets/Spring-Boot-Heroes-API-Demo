package com.springsamples.heroesapi.repositories;

import com.springsamples.heroesapi.repositories.entities.HeroEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class HeroesRepositoryCommandImpl implements HeroesRepositoryCommand {

    private static final String UPDATE_HERO_QUERY = "update HEROES set name = ? where id = ?";
    private static final String DELETE_HERO_QUERY = "delete from HEROES where id = ?";
    private final JdbcTemplate template;

    @Override
    public int update(HeroEntity entity) {
        return template.update(UPDATE_HERO_QUERY, entity.getName(), entity.getId().toString());
    }

    @Override
    public int delete(UUID id) {
        return template.update(DELETE_HERO_QUERY, id.toString());
    }
}
