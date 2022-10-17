package com.springsamples.heroesapi.web;

import com.springsamples.heroesapi.config.aspects.LogExecutionTime;
import com.springsamples.heroesapi.exceptions.HeroNotFoundException;
import com.springsamples.heroesapi.exceptions.model.ErrorDto;
import com.springsamples.heroesapi.services.HeroesFacade;
import com.springsamples.heroesapi.web.model.HeroDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

    @Operation(summary = "Find all heroes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Heroes found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = HeroDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            })
    })
    @GetMapping
    @LogExecutionTime
    public ResponseEntity<?> heroes() {
        var heroes = facade.findAll();
        return ResponseEntity.ok(heroes);
    }

    @Operation(summary = "Find all heroes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hero found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = HeroDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Hero not found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid ID", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            })
    })
    @GetMapping("/{id}")
    @LogExecutionTime
    public ResponseEntity<?> hero(@PathVariable UUID id) {
        return facade.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new HeroNotFoundException(id));
    }

    @Operation(summary = "Find all heroes by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Heroes found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = HeroDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid Filter", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            })
    })
    @GetMapping("/filter")
    @LogExecutionTime
    public ResponseEntity<?> heroesByName(@Valid
                                          @NotBlank(message = "Filter must not be blank")
                                          @Length(min = 1, max = 20, message = "Filter length must be between 1 and 20")
                                          @RequestParam String name) {
        var heroesByName = facade.findByNameContains(name);
        return ResponseEntity.ok(heroesByName);
    }

    @Operation(summary = "Update Hero")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Hero updated"),
            @ApiResponse(responseCode = "400", description = "Invalid Filter", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Hero not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            })
    })
    @PutMapping
    @LogExecutionTime
    public ResponseEntity<?> updateHero(@Valid @RequestBody HeroDto dto) {
        facade.updateHero(dto);
        var location = String.format(BASE_URL + "/%s", dto.getId().toString());
        return ResponseEntity.noContent()
                .header(HttpHeaders.LOCATION,location)
                .build();
    }

    @Operation(summary = "Delete Hero")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Hero deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid ID", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Hero not found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            })
    })
    @DeleteMapping("/{id}")
    @LogExecutionTime
    public ResponseEntity<?> deleteHero(@PathVariable UUID id) {
        facade.deleteHero(id);
        return new ResponseEntity<>(id, HttpStatus.ACCEPTED);
    }
}
