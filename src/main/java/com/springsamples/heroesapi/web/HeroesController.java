package com.springsamples.heroesapi.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HeroesController {

    @GetMapping
    public ResponseEntity<?> heroes() {
        return ResponseEntity.ok().build();
    }
}
