package com.mf.minutefictionbackend.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateCorrectUser() throws Exception {

        String requestJson = """
                {
                    "username" : "intuser",
                    "password" : "passwordint",
                    "email" : "intemail@email.com",
                    "subscribedToMailing" : false
                }
                """;

        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/users")
                            .contentType(APPLICATION_JSON)
                            .content(requestJson))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().isCreated())
                        .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);

        String createdUsername = jsonNode.get("username").asText();
        String createdEmail = jsonNode.get("email").asText();
        boolean subscribedToMailing = jsonNode.get("subscribedToMailing").asBoolean();
        boolean hasAuthorProfile = jsonNode.get("hasAuthorProfile").asBoolean();

        assertThat(result.getResponse().getHeader("Location"), matchesPattern("^.*/users/" + createdUsername));

        assertEquals("intuser", createdUsername);
        assertEquals("intemail@email.com", createdEmail);
        assertFalse(subscribedToMailing);
        assertFalse(hasAuthorProfile);


    }



}