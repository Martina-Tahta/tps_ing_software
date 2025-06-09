package org.udesa.unoback.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc

//curl -X POST "http://localhost:8080/newmatch?players=A&players=B" -H "Accept: application/json"
//curl -X GET "http://localhost:8080/activecard/d2add54b-612b-4f49-9da0-0bf51b325eb2" -H "Accept: application/json"


public class UnoControllerTest {
    @Autowired private MockMvc mockMvc;

    private String createMatch() throws Exception {
        MvcResult result = mockMvc.perform(post("/newmatch")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("players", "Alice", "Bob"))
                .andExpect(status().isOk())
                .andReturn();

        //return result.getResponse().getContentAsString();
        String raw = result.getResponse().getContentAsString();
        // parsea el JSON string a String puro
        return new ObjectMapper().readValue(raw, String.class);
    }

    @Test
    public void test01_NewMatchReturnsOkAndResponseNotEmpty() throws Exception {
        String matchId = createMatch();
        assertTrue(matchId != null);
        UUID.fromString(matchId);
    }

    @Test
    public void test02_NewMatchFailsWithNoPlayers() throws Exception {
        mockMvc.perform(post("/newmatch")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test03_PlayerHandReturnsHand() throws Exception {
        String matchId = createMatch();

        mockMvc.perform(get("/playerhand/" + matchId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void test04_ActiveCardReturnsCard() throws Exception {
        String matchId = createMatch();

        mockMvc.perform(get("/activecard/" + matchId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.color").exists());
    }

    @Test
    public void test05_DrawCardReturnsConfirmationMessage() throws Exception {
        String matchId = createMatch();

        mockMvc.perform(post("/draw/" + matchId + "/Alice"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("drew")));
    }

    @Test
    public void test06_PlayCardReturnsConfirmationMessage() throws Exception {
        //esto no funciona porque estamos repartiendo cartas random, no sabemos si tiene esa carta
        String matchId = createMatch();

        String cardJson = """
                {
                  "color": "Red",
                  "value": "5",
                  "type": "NumberCard"
                }
                """;

        mockMvc.perform(post("/play/" + matchId + "/Alice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cardJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("thrown")));
    }

}
