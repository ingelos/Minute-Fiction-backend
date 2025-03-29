package com.mf.minutefictionbackend.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = true)
@ActiveProfiles("test")
class ThemeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @WithMockUser(username = "fakeeditor", authorities = "EDITOR")
    void shouldCreateNewTheme() throws Exception {

        String requestJson = """
                {
                    "name" : "faketheme",
                    "description" : "This is the description of the theme",
                    "openDate" : "2025-05-05",
                    "closingDate" : "2025-12-12"
                }
                """;

        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/themes")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);

        String createdThemeName = jsonNode.get("name").asText();
        assertThat(result.getResponse().getHeader("Location"), matchesPattern("^.*/themes/" + createdThemeName));


        String createdDescription = jsonNode.get("description").asText();
        String openDateStr = jsonNode.get("openDate").asText();
        String closingDateStr = jsonNode.get("closingDate").asText();
        LocalDate createdOpenDate = LocalDate.parse(openDateStr);
        LocalDate createdClosingDate = LocalDate.parse(closingDateStr);

        assertEquals("faketheme", createdThemeName);
        assertEquals("This is the description of the theme", createdDescription);
        assertEquals(LocalDate.of(2025, 5, 5), createdOpenDate);
        assertEquals(LocalDate.of(2025, 12, 12), createdClosingDate);

    }

}