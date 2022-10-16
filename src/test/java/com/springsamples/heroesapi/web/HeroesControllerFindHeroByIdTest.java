package com.springsamples.heroesapi.web;

import com.springsamples.heroesapi.services.HeroesFacade;
import com.springsamples.heroesapi.web.model.HeroDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;
import java.util.UUID;

import static com.springsamples.heroesapi.constants.Test.*;
import static com.springsamples.heroesapi.constants.Web.BASE_URL;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.reset;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class HeroesControllerFindHeroByIdTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private HeroesFacade facade;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        given(facade.findById(ArgumentMatchers.any())).willReturn(Optional.of(
                HeroDto.builder()
                        .id(UUID.randomUUID())
                        .name(BATMAN)
                        .build()
        ));
    }

    @AfterEach
    void afterEach() {
        reset(facade);
    }

    @Test
    @DisplayName("Should return 200 OK response with ID param")
    public void findHeroById_200() throws Exception {
        this.mockMvc.perform(get(BASE_URL + "/{id}", VALID_HERO_ID)
                        .with(user(USERNAME)))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("Should return 400 when ID param is invalid, and return an appropriate message")
    public void findHeroById_400_ErrorDetail_WhenInValidId() throws Exception {
        this.mockMvc.perform(get(BASE_URL + "/{id}", INVALID_HERO_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user(USERNAME)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.reason", is(INVALID_ID_FORMAT_MESSAGE)));
    }

    @Test
    @DisplayName("Should find a hero when ID is valid, and return a 200 code response")
    public void findHeroById_200_NotNull() throws Exception {
        this.mockMvc.perform(get(BASE_URL + "/{id}", VALID_HERO_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user(USERNAME)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    @DisplayName("Should get hero by ID from facade")
    public void findHeroById_facadeInteraction() throws Exception {
        then(facade).shouldHaveNoInteractions();
        this.mockMvc.perform(get(BASE_URL + "/{id}", VALID_HERO_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user(USERNAME)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()));
        then(facade).should(only()).findById(UUID.fromString(VALID_HERO_ID));
        then(facade).shouldHaveNoMoreInteractions();
    }

    @Test
    @DisplayName("Should return 404 code error with detail message when hero was not found")
    public void findHeroById_404_HeroNotFound_errorDetail() throws Exception {
        given(facade.findById(ArgumentMatchers.any())).willReturn(Optional.empty());
        then(facade).shouldHaveNoInteractions();
        this.mockMvc.perform(get(BASE_URL + "/{id}", NOT_FOUND_HERO_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user(USERNAME)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.reason", is(NOT_FOUND_HERO_MESSAGE)));
        then(facade).should(only()).findById(UUID.fromString(NOT_FOUND_HERO_ID));
        then(facade).shouldHaveNoMoreInteractions();
    }

}
