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
//curl -X GET "http://localhost:8080/activecard/6189c910-2f60-4ab5-bccf-ddffdba6fb06" -H "Accept: application/json"
//curl -X GET "http://localhost:8080/playerhand/6189c910-2f60-4ab5-bccf-ddffdba6fb06" -H "Accept: application/json"
//curl -X POST "http://localhost:8080/draw/57f7a395-1339-4fcf-89d3-3e0a54ebd150/A"


public class UnoControllerTest {
    @Autowired private MockMvc mockMvc;

    private String createMatch() throws Exception {
        MvcResult result = mockMvc.perform(post("/newmatch")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("players", "Alice", "Bob"))
                .andExpect(status().isOk())
                .andReturn();

        String raw = result.getResponse().getContentAsString();
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
                .andExpect(status().is(400));
    }

    @Test
    public void test03_NewMatchFailsWithEmptyPlayers() throws Exception {
        mockMvc.perform(post("/newmatch")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("players", "", ""))
                .andExpect(status().is(500));
    }

    @Test
    public void test04_NewMatchFailsWithOnePlayer() throws Exception {
        mockMvc.perform(post("/newmatch")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("players", "Alice"))
                .andExpect(status().is(500));
    }

    @Test
    public void test05_PlayerHandReturnsHand() throws Exception {
        String matchId = createMatch();

        mockMvc.perform(get("/playerhand/" + matchId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void test06_PlayerHandFailsWithNoId() throws Exception {
        mockMvc.perform(get("/playerhand/"))
                .andDo(print())
                .andExpect(status().is(404));
    }

    @Test
    public void test07_PlayerHandWrongId() throws Exception {
        UUID nonExistantId = UUID.randomUUID();
        mockMvc.perform(get("/playerhand/" + nonExistantId))
                .andDo(print())
                .andExpect(status().is(400));
    }


    @Test
    public void test08_ActiveCardReturnsCard() throws Exception {
        String matchId = createMatch();

        mockMvc.perform(get("/activecard/" + matchId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.color").exists());
    }

    @Test
    public void test09_ActiveCardFailsWithNoId() throws Exception {
        mockMvc.perform(get("/activecard/"))
                .andDo(print())
                .andExpect(status().is(404));
    }

    @Test
    public void test10_ActiveCardWrongId() throws Exception {
        UUID nonExistantId = UUID.randomUUID();
        mockMvc.perform(get("/activecard/" + nonExistantId))
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    public void test11_DrawCardReturnsConfirmationMessage() throws Exception {
        String matchId = createMatch();

        mockMvc.perform(post("/draw/" + matchId + "/Alice"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void test12_DrawCardFailsWithNoIdAndPlayer() throws Exception {
        mockMvc.perform(post("/draw/"))
                .andDo(print())
                .andExpect(status().is(404));
    }

    @Test
    public void test13_DrawCardFailsWithNoPlayer() throws Exception {
        String matchId = createMatch();

        mockMvc.perform(post("/draw/" + matchId))
                .andDo(print())
                .andExpect(status().is(404));
    }

    @Test
    public void test14_DrawCardWrongId() throws Exception {
        UUID nonExistantId = UUID.randomUUID();
        mockMvc.perform(post("/draw/" + nonExistantId + "/Alice"))
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    public void test15_DrawCardFailsWithWrongPlayer() throws Exception {
        String matchId = createMatch();

        mockMvc.perform(post("/draw/" + matchId + "/A"))
                .andDo(print())
                .andExpect(status().is(500));
    }



    //play:
    // - error formato json
    // - falta parametros: id, player, carta
    // - error modelo: id incorrecto, jugador incorrect, no tiene la carta, no la puede jugar
    @Test
    public void test06_PlayCardReturnsConfirmationMessage() throws Exception {
        //esto no funciona porque estamos repartiendo cartas random, no sabemos si tiene esa carta
        String matchId = createMatch();

        String cardJson = """
                {
                  "color": "Red",
                  "number": "5",
                  "type": "NumberCard",
                  "status": true
                }
                """;

        mockMvc.perform(post("/play/" + matchId + "/Alice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cardJson))
                .andDo(print())
                .andExpect(status().isOk());
                //.andExpect(content().string(containsString("thrown")));
    }

}
