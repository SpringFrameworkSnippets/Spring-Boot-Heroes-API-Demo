package com.springsamples.heroesapi.web;

import com.springsamples.heroesapi.services.HeroesFacade;
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

import static com.springsamples.heroesapi.constants.Test.*;
import static com.springsamples.heroesapi.constants.Web.BASE_URL;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class HeroesControllerDeleteHeroTest {

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

    }

    @Test
    @DisplayName("Should return 202 Accepted")
    void deleteHero_204() throws Exception {
        this.mockMvc.perform(delete(BASE_URL + "/{id}", BATMAN_VALID_HERO_ID)
                        .with(user(USERNAME))
                        .with(csrf()))
                .andExpect(status().isAccepted());
    }

    @Test
    @DisplayName("Should return 400 when ID param is invalid, and return an appropriate message")
    public void deleteHero_400_ErrorDetail_WhenInValidId() throws Exception {
        // This behavior was already cover when we implemented ID validation for findHeroById op.
        this.mockMvc.perform(delete(BASE_URL + "/{id}", INVALID_HERO_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user(USERNAME))
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.reason", is(INVALID_ID_FORMAT_MESSAGE)));
    }

    @Test
    @DisplayName("Should delete hero calling facade")
    public void deleteHero_withFacadeInteraction() throws Exception {
        then(facade).shouldHaveNoInteractions();
        this.mockMvc.perform(delete(BASE_URL + "/{id}", BATMAN_VALID_HERO_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user(USERNAME))
                        .with(csrf()))
                .andExpect(status().isAccepted());
        then(facade).should(only()).deleteHero(any());
        then(facade).shouldHaveNoMoreInteractions();
    }

}
