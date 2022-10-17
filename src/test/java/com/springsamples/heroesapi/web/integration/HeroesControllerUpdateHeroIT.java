package com.springsamples.heroesapi.web.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springsamples.heroesapi.web.model.HeroDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.springsamples.heroesapi.constants.Test.TRACER_VALID_HERO_ID;
import static com.springsamples.heroesapi.constants.Test.USERNAME;
import static com.springsamples.heroesapi.constants.Web.BASE_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Tag("Integration")
@SpringBootTest
public class HeroesControllerUpdateHeroIT {
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
    @DisplayName("Should get 204 response and update hero with valid ID")
    public void updateHero() throws Exception {
        MvcResult resultFindHeroById = this.mockMvc.perform(get(BASE_URL + "/{id}", TRACER_VALID_HERO_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user(USERNAME)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andReturn();

        var forUpdate = objectMapper.readValue(resultFindHeroById.getResponse().getContentAsString(), HeroDto.class);
        final var nameBefore = forUpdate.getName();

        MvcResult resultUpdateHero = this.mockMvc.perform(put(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(HeroDto.builder()
                                .id(forUpdate.getId())
                                .name("Updated " + nameBefore)
                                .build()))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(user(USERNAME))
                        .with(csrf()))
                .andExpect(status().isNoContent())
                .andReturn();

        MvcResult resultFindHeroByIdUpdated = this.mockMvc.perform(get(BASE_URL + "/{id}", TRACER_VALID_HERO_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user(USERNAME)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andReturn();

        var updated = objectMapper.readValue(resultFindHeroByIdUpdated.getResponse().getContentAsString(), HeroDto.class);
        assertThat(updated.getName()).isNotEqualTo(nameBefore);
        assertThat(resultUpdateHero.getResponse().getHeader(HttpHeaders.LOCATION)).isEqualTo(BASE_URL + "/" + updated.getId().toString());
    }
}
