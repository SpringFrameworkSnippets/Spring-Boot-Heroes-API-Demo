package com.springsamples.heroesapi.web.integration;

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

import java.util.UUID;

import static com.springsamples.heroesapi.constants.Test.BATMAN_VALID_HERO_ID;
import static com.springsamples.heroesapi.constants.Test.USERNAME;
import static com.springsamples.heroesapi.constants.Web.BASE_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Tag("Integration")
@SpringBootTest
public class HeroesControllerFindHeroByIdIT {
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
    @DisplayName("Should get 200 OK response with hero by ID")
    public void findHero() throws Exception {
        MvcResult result = this.mockMvc.perform(get(BASE_URL + "/{id}", BATMAN_VALID_HERO_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user(USERNAME)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        var dto = objectMapper.readValue(contentAsString, HeroDto.class);
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(UUID.fromString(BATMAN_VALID_HERO_ID));
    }
}
