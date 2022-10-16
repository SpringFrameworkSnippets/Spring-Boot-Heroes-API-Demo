package com.springsamples.heroesapi.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springsamples.heroesapi.web.model.HeroDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.springsamples.heroesapi.constants.Test.*;
import static com.springsamples.heroesapi.constants.Web.BASE_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Tag("Integration")
@SpringBootTest
public class HeroesControllerIT {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("Should get 200 OK response with hero list")
    public void findHeroes() throws Exception {
        MvcResult result = this.mockMvc.perform(get(BASE_URL)
                        .with(user(USERNAME)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].id", not(emptyString())))
                .andExpect(jsonPath("$[0].name", not(emptyString())))
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        var heroes = objectMapper.readValue(contentAsString, new TypeReference<List<HeroDto>>() {});
        assertThat(heroes).isNotEmpty();
    }

    @Test
    @DisplayName("Should get 200 OK response with hero by ID")
    public void findHero() throws Exception {
        MvcResult result = this.mockMvc.perform(get(BASE_URL + "/{id}", VALID_HERO_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user(USERNAME)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        var dto = objectMapper.readValue(contentAsString, HeroDto.class);
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(UUID.fromString(VALID_HERO_ID));
    }

    @Test
    @DisplayName("Should get 200 OK response with hero matching filter name")
    public void findHeroByName() throws Exception {
        MvcResult result = this.mockMvc.perform(get(BASE_URL + "/filter")
                        .with(user(USERNAME))
                        .queryParam(FILTER_PARAM_NAME, FILTER_PARAM_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        var heroes = objectMapper.readValue(contentAsString, new TypeReference<List<HeroDto>>() {});
        assertThat(heroes).hasSize(2);
        assertThat(heroes.stream().map(HeroDto::getName)
                .collect(Collectors.toList()))
                .isEqualTo(List.of(BATMAN, SUPERMAN));
    }
}
