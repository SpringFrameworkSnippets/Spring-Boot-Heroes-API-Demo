package com.springsamples.heroesapi.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springsamples.heroesapi.services.HeroesFacade;
import com.springsamples.heroesapi.web.model.HeroDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static com.springsamples.heroesapi.constants.Test.USERNAME;
import static com.springsamples.heroesapi.constants.Web.BASE_URL;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class HeresControllerUpdateHeroTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private HeroesFacade facade;

    private MockMvc mockMvc;

    private HeroDto validContent;
    private HeroDto invalidContent;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        validContent = HeroDto.builder()
                .id(UUID.randomUUID())
                .name("dto")
                .build();

        invalidContent = HeroDto.builder()
                .id(UUID.randomUUID())
                .name("")
                .build();
    }

    @Test
    @DisplayName("Should return 204 response with payload")
    void updateHero() throws Exception {
        this.mockMvc.perform(put(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(validContent))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(user(USERNAME))
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should return 400 response with error detail when invalid payload")
    void updateHero_InvalidPayload() throws Exception {
        this.mockMvc.perform(put(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(invalidContent))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(user(USERNAME))
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.reason", is("Invalid Hero")));
    }
}
