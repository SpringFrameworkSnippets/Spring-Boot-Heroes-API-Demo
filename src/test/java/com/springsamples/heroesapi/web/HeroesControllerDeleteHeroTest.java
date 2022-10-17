package com.springsamples.heroesapi.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.springsamples.heroesapi.constants.Test.BATMAN_VALID_HERO_ID;
import static com.springsamples.heroesapi.constants.Test.USERNAME;
import static com.springsamples.heroesapi.constants.Web.BASE_URL;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class HeroesControllerDeleteHeroTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

    }

    @Test
    @DisplayName("Should return 202 Accepted")
    void deleteHero_204() throws Exception {
        this.mockMvc.perform(delete(BASE_URL + "/{id}", BATMAN_VALID_HERO_ID)
                .with(user(USERNAME)))
                .andExpect(status().isAccepted());
    }
}
