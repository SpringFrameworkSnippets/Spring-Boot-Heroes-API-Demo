package com.springsamples.heroesapi.web;

import com.springsamples.heroesapi.services.HeroesFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.springsamples.heroesapi.constants.Web.BASE_URL;

@RestController
@RequestMapping(BASE_URL)
@RequiredArgsConstructor
public class HeroesController {

    private final HeroesFacade facade;

    @GetMapping
    public ResponseEntity<?> heroes() {
        var heroes = facade.findAll();
        return ResponseEntity.ok(heroes);
    }
}
