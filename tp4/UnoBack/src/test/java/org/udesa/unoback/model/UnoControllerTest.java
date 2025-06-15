package org.udesa.unoback.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.udesa.unoback.service.Dealer;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc


public class UnoControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private Dealer dealer;

    static Card RedOn( int n ) { return new NumberCard( "Red", n ); }
    static Card BlueOn( int n ) { return new NumberCard( "Blue", n ); }
    static Card YellowOn( int n ) { return new NumberCard( "Yellow", n ); }
    static Card GreenOn( int n ) { return new NumberCard( "Green", n ); }
    static Card red1 = RedOn( 1 );
    static Card red2 = RedOn( 2 );
    static Card red3 = RedOn( 3 );
    static Card red4 = RedOn( 4 );
    static Card red5 = RedOn( 5 );
    static Card blue1 = BlueOn( 1 );
    static Card blue2 = BlueOn( 2 );
    static Card blue3 = BlueOn( 3 );
    static Card green1 = GreenOn( 1 );
    static Card green3 = GreenOn( 3 );
    static Card green5 = GreenOn( 5 );
    static Card yellow3 = YellowOn( 3 );
    static Card yellow5 = YellowOn( 5 );


    private String createMatch() throws Exception {
        MvcResult result = mockMvc.perform(post("/newmatch")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("players", "Alice", "Bob"))
                .andExpect(status().isOk())
                .andReturn();

        String raw = result.getResponse().getContentAsString();
        return new ObjectMapper().readValue(raw, String.class);
    }



    @BeforeEach
    public void setUp() {
        List<Card> deck = deck();
        when(dealer.fullDeck()).thenReturn(deck);
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
    public void test03_NewMatchFailsWithEmptyPlayers() throws Exception {
        mockMvc.perform(post("/newmatch")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("players", "", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test04_NewMatchFailsWithOnePlayer() throws Exception {
        mockMvc.perform(post("/newmatch")
                        .param("players", "Alice"))
                .andExpect(status().isBadRequest());
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
                .andExpect(status().isBadRequest());
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
                .andExpect(status().isBadRequest());
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
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test15_DrawCardFailsWithWrongPlayer() throws Exception {
        String matchId = createMatch();

        mockMvc.perform(post("/draw/" + matchId + "/A"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test16_PlayCardReturnsConfirmationMessage() throws Exception {
        String matchId = createMatch();

        String cardJson = """
                {
                  "color": "Blue",
                  "number": "1",
                  "type": "NumberCard",
                  "shout": false
                }
                """;

        mockMvc.perform(post("/play/" + matchId + "/Alice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cardJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void test17_PlayCardFailsWithMalformedJson() throws Exception {
        String matchId = createMatch();

        String malformedJson = """
            {
              "color": "Blue",
              "number": "1",
              "type": "NumberCard",
              "shout": false
            """;

        mockMvc.perform(post("/play/" + matchId + "/Alice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(malformedJson))
                .andDo(print())
                .andExpect(status().isBadRequest());

        String malformedJson2 = """
            {
              "color": "Blue",
              "number": "red",
              "type": "NumberCard",
              "shout": false
            }
            """;

        mockMvc.perform(post("/play/" + matchId + "/Alice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(malformedJson2))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test18_PlayCardFailsWithMissingParameters() throws Exception {
        String cardJson = """
            {
              "color": "Red",
              "number": "3",
              "type": "NumberCard",
              "shout": false
            }
            """;

        mockMvc.perform(post("/play/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cardJson))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void test19_PlayCardFailsWithInvalidMatchId() throws Exception {
        UUID invalidMatchId = UUID.randomUUID();
        String cardJson = """
            {
              "color": "Red",
              "number": "3",
              "type": "NumberCard",
              "shout": false
            }
            """;

        mockMvc.perform(post("/play/" + invalidMatchId + "/Alice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cardJson))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test20_PlayCardFailsWithInvalidPlayer() throws Exception {
        String matchId = createMatch();
        String cardJson = """
            {
              "color": "Red",
              "number": "3",
              "type": "NumberCard",
              "shout": false
            }
            """;

        mockMvc.perform(post("/play/" + matchId + "/UnknownPlayer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cardJson))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test21_PlayCardFailsWhenPlayerDoesNotHaveCard() throws Exception {
        String matchId = createMatch();
        String cardJson = """
            {
              "color": "Yellow",
              "number": "5",
              "type": "NumberCard",
              "shout": false
            }
            """;

        mockMvc.perform(post("/play/" + matchId + "/Alice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cardJson))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test22_PlayCardFailsWhenPlayerCannotPlayCard() throws Exception {
        String matchId = createMatch();
        String cardJson = """
            {
              "color": "Blue",
              "number": "2",
              "type": "NumberCard",
              "shout": false
            }
            """;

        mockMvc.perform(post("/play/" + matchId + "/Alice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cardJson))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    private static List<Card> deck() {
        return List.of( red1,
                blue1, blue2, green3, blue3, yellow3, red3, red4,
                blue1, blue2, green3, blue3, yellow3, red3, red4,
                green1, green5, red2, red4, red5);
    }
}
