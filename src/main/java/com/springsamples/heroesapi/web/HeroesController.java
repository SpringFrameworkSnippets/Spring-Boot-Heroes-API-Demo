package com.springsamples.heroesapi.web;

import com.springsamples.heroesapi.config.aspects.LogExecutionTime;
import com.springsamples.heroesapi.exceptions.HeroNotFoundException;
import com.springsamples.heroesapi.services.HeroesFacade;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

import static com.springsamples.heroesapi.constants.Web.BASE_URL;

@RestController
@RequestMapping(BASE_URL)
@RequiredArgsConstructor
@Validated
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
    @LogExecutionTime
    public ResponseEntity<?> heroesByName(@Valid
                                          @NotBlank(message = "Filter must not be blank")
                                          @Length(min = 1, max = 20, message = "Filter length must be between 1 and 20")
                                          @RequestParam String name) {
        var heroesByName = facade.findByNameContains(name);
        return ResponseEntity.ok(heroesByName);
    }
}
