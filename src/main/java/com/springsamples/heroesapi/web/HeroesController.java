package com.springsamples.heroesapi.web;

import com.springsamples.heroesapi.config.aspects.LogExecutionTime;
import com.springsamples.heroesapi.exceptions.HeroNotFoundException;
import com.springsamples.heroesapi.services.HeroesFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.springsamples.heroesapi.constants.Web.BASE_URL;

@RestController
@RequestMapping(BASE_URL)
@RequiredArgsConstructor
public class HeroesController {

    private final HeroesFacade facade;

    @GetMapping
    @LogExecutionTime
    public ResponseEntity<?> heroes() {
        var heroes = facade.findAll();
        return ResponseEntity.ok(heroes);
    }

    @GetMapping("/{id}")
    @LogExecutionTime
    public ResponseEntity<?> hero(@PathVariable UUID id) {
        return facade.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new HeroNotFoundException(id));
    }

    @GetMapping("/filter")
    public ResponseEntity<?> heroesByName(@RequestParam String name) {
        return ResponseEntity.ok().build();
    }
}
