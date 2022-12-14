package com.springsamples.heroesapi.web;

import com.springsamples.heroesapi.services.HeroesFacade;
import com.springsamples.heroesapi.web.model.HeroDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
public class HeroesControllerFindHeroesTest {

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

        given(facade.findAll()).willReturn(List.of(
                HeroDto.builder()
                        .id(UUID.randomUUID())
                        .name(BATMAN)
                        .build(),
                HeroDto.builder()
                        .id(UUID.randomUUID())
                        .name(SUPERMAN)
                        .build()
        ));
    }

    @AfterEach
    void afterEach() {
        reset(facade);
    }

    @Test
    @DisplayName("Should get 200 OK response")
    public void findHeroes_200() throws Exception {
        this.mockMvc.perform(get(BASE_URL)
                        .with(user(USERNAME)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should get 200 OK not empty response")
    public void findHeroes_200_NotEmpty() throws Exception {
        this.mockMvc.perform(get(BASE_URL)
                        .with(user(USERNAME)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", not(empty())));
    }

    @Test
    @DisplayName("Should get 200 OK hero list response")
    public void findHeroes_200_HeroList() throws Exception {
        this.mockMvc.perform(get(BASE_URL)
                        .with(user(USERNAME)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].id", not(emptyString())))
                .andExpect(jsonPath("$[0].name", not(emptyString())));
    }

    @Test
    @DisplayName("Should get 401 unauthorized without basic auth")
    public void findHeroes_Unauthorized() throws Exception {
        this.mockMvc.perform(get(BASE_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Should get hero list from facade")
    public void findHeroes_facadeInteraction() throws Exception {
        then(facade).shouldHaveNoInteractions();
        this.mockMvc.perform(get(BASE_URL)
                        .with(user(USERNAME)))
                .andExpect(status().isOk());
        then(facade).should(only()).findAll();
    }
}
