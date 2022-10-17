package com.springsamples.heroesapi.web.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springsamples.heroesapi.web.model.HeroDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.springsamples.heroesapi.constants.Test.SOLDIER_VALID_HERO_ID;
import static com.springsamples.heroesapi.constants.Test.USERNAME;
import static com.springsamples.heroesapi.constants.Web.BASE_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Tag("Integration")
@SpringBootTest
public class HeroesControllerDeleteHeroIT {
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
    @DisplayName("Should get 202 response and delete hero with valid ID")
    public void deleteHero() throws Exception {
        MvcResult resultFindHeroById = this.mockMvc.perform(get(BASE_URL + "/{id}", SOLDIER_VALID_HERO_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user(USERNAME)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andReturn();

        var forDelete = objectMapper.readValue(resultFindHeroById.getResponse().getContentAsString(), HeroDto.class);
        final var idForDelete = forDelete.getId().toString();

        MvcResult resultDeleteHero = this.mockMvc.perform(delete(BASE_URL + "/{id}", idForDelete)
                        .with(user(USERNAME))
                        .with(csrf()))
                .andExpect(status().isAccepted())
                .andReturn();

        var idDeleted = objectMapper.readValue(resultDeleteHero.getResponse().getContentAsString(), String.class);
        assertThat(idDeleted).isEqualTo(idForDelete);

        this.mockMvc.perform(get(BASE_URL + "/{id}", idDeleted)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user(USERNAME)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.reason", is("Hero with id " + idDeleted + " not found")));

    }
}
