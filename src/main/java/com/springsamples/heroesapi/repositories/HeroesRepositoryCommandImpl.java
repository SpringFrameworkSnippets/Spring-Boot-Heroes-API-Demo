package com.springsamples.heroesapi.repositories;

import com.springsamples.heroesapi.repositories.entities.HeroEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class HeroesRepositoryCommandImpl implements HeroesRepositoryCommand {

    private final JdbcTemplate template;

    @Override
    public int update(HeroEntity entity) {
        String query = "update HEROES set name = ? where id = ?";
        return template.update(query, entity.getName(), entity.getId().toString());
    }

    @Override
    public int delete(UUID id) {
        return 0;
    }
}
