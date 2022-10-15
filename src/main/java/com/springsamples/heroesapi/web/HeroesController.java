package com.springsamples.heroesapi.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.springsamples.heroesapi.constants.Web.BASE_URL;

@RestController
@RequestMapping(BASE_URL)
public class HeroesController {

    @GetMapping
    public ResponseEntity<?> heroes() {
        var heroes = List.of("Superman", "batman");
        return ResponseEntity.ok(heroes);
    }
}
