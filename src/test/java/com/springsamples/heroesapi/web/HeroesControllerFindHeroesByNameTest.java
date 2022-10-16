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

import java.util.List;
import java.util.UUID;

import static com.springsamples.heroesapi.constants.Test.*;
import static com.springsamples.heroesapi.constants.Web.BASE_URL;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.reset;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class HeroesControllerFindHeroesByNameTest {

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

        given(facade.findByNameContains(ArgumentMatchers.any())).willReturn(List.of(
                HeroDto.builder()
                        .id(UUID.randomUUID())
                        .name("He-Man")
                        .build()
        ));
    }

    @AfterEach
    void afterEach() {
        reset(facade);
    }

    @Test
    @DisplayName("Should return 200 Ok response with query param")
    public void findHeroByName_200() throws Exception {
        this.mockMvc.perform(get(BASE_URL + "/filter")
                        .with(user(USERNAME))
                        .queryParam(FILTER_PARAM_NAME, FILTER_PARAM_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should return 400 response with error detail when filter size is invalid")
    public void findHeroByName_400_whenFilterInvalidSize() throws Exception {
        this.mockMvc.perform(get(BASE_URL + "/filter")
                        .with(user(USERNAME))
                        .queryParam(FILTER_PARAM_NAME, HERO_REQUEST_PARAM_INVALID_SIZE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.reason", is(INVALID_SIZE_FILTER_MESSAGE)));
    }

    @Test
    @DisplayName("Should return 400 response with error detail when filter is blank")
    public void findHeroByName_400_whenFilterBlank() throws Exception {
        this.mockMvc.perform(get(BASE_URL + "/filter")
                        .with(user(USERNAME))
                        .queryParam(FILTER_PARAM_NAME, HERO_REQUEST_PARAM_BLANK))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.reason", containsStringIgnoringCase(INVALID_SIZE_FILTER_MESSAGE)))
                .andExpect(jsonPath("$.reason", containsStringIgnoringCase(BLANK_FILTER_MESSAGE)));
    }

    @Test
    @DisplayName("Should get hero by name from facade")
    public void findHeroByName_facadeInteraction() throws Exception {
        then(facade).shouldHaveNoInteractions();
        this.mockMvc.perform(get(BASE_URL + "/filter")
                        .with(user(USERNAME))
                        .queryParam(FILTER_PARAM_NAME, FILTER_PARAM_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()));
        then(facade).should(only()).findByNameContains(FILTER_PARAM_VALUE);
        then(facade).shouldHaveNoMoreInteractions();
    }
}
