package com.springsamples.heroesapi.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springsamples.heroesapi.web.model.HeroDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static com.springsamples.heroesapi.constants.Test.USERNAME;
import static com.springsamples.heroesapi.constants.Web.BASE_URL;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@JsonTest
public class HeresControllerUpdateHeroTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper mapper;

    private MockMvc mockMvc;

    private HeroDto content;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        content = HeroDto.builder()
                .id(UUID.randomUUID())
                .name("dto")
                .build();
    }

    @Test
    @DisplayName("Should return 204 response with payload")
    void updateHero() throws Exception {
        this.mockMvc.perform(put(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(content))
                        .with(user(USERNAME)))
                .andExpect(status().isNoContent());
    }
}
