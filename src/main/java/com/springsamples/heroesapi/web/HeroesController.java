package com.springsamples.heroesapi.web;

import com.springsamples.heroesapi.web.model.HeroDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static com.springsamples.heroesapi.constants.Web.BASE_URL;

@RestController
@RequestMapping(BASE_URL)
public class HeroesController {

    @GetMapping
    public ResponseEntity<?> heroes() {
        var heroes = List.of(HeroDto.builder()
                .id(UUID.randomUUID())
                .name("Batman")
                .build());
        return ResponseEntity.ok(heroes);
    }
}
