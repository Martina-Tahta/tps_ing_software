package org.udesa.unoback.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.udesa.unoback.service.UnoService;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UnoServiceTest {
    @Autowired private UnoService unoService;

    @Test public void newMatchTest() {
        UUID id = unoService.newMatch(List.of("A", "B"), null);
        assertNotNull(id);
    }

    @Test public void playerHandTest() {
        UUID id = unoService.newMatch(List.of("A", "B"), null);
        assertNotNull(unoService.getPlayerHand(id));
    }

    @Test public void playerHandIdError() {
        UUID nonExistantId = UUID.randomUUID();
        assertThrows(RuntimeException.class, () -> unoService.getPlayerHand(nonExistantId));
    }

    @Test public void activeCardTest() {
        UUID id = unoService.newMatch(List.of("A", "B"), null);
        assertNotNull(unoService.getActiveCard(id));
    }

    @Test public void activeCardIdError() {
        UUID nonExistantId = UUID.randomUUID();
        assertThrows(RuntimeException.class, () -> unoService.getActiveCard(nonExistantId));
    }

    @Test public void drawCardTest() {
        UUID id = unoService.newMatch(List.of("A", "B"), null);
        unoService.drawCard(id, "A");
    }

    @Test public void drawCardIdError() {
        UUID nonExistantId = UUID.randomUUID();
        assertThrows(RuntimeException.class, () -> unoService.drawCard(nonExistantId, "A"));
    }

    @Test public void drawCardIncorrectPlayer() {
        UUID id = unoService.newMatch(List.of("A", "B"), null);
        assertThrows(RuntimeException.class, () -> unoService.drawCard(id, "B"));
        assertThrows(RuntimeException.class, () -> unoService.drawCard(id, "C"));
    }

//    @Test public void drawCardGameEnded() {
//        UUID id = unoService.newMatch(List.of("A", "B", null));
//        //deberia finalizar el juego...
//        assertThrows(RuntimeException.class, () -> unoService.drawCard(id, "B"));
//    }

    @Test public void playCardTest() {
        List<Card> deck = createCustomDeck();
        UUID id = unoService.newMatch(List.of("A", "B"), deck);
        Card card = new NumberCard("Red", 1);
        unoService.play(id, "A", card.asJson());
    }

    @Test public void playCardIdError() {
        UUID nonExistantId = UUID.randomUUID();
        Card card = new NumberCard("Red", 1);
        assertThrows(RuntimeException.class, () -> unoService.play(nonExistantId, "A", card.asJson()));
    }

    @Test public void playCardIncorrectPlayer() {
        List<Card> deck = createCustomDeck();
        UUID id = unoService.newMatch(List.of("A", "B"), deck);
        Card card = new NumberCard("Red", 7);
        assertThrows(RuntimeException.class, () -> unoService.play(id, "B", card.asJson()));
    }

    @Test public void playCardPlayerNotCard() {
        List<Card> deck = createCustomDeck();
        UUID id = unoService.newMatch(List.of("A", "B"), deck);
        Card card = new NumberCard("Red", 7);
        assertThrows(RuntimeException.class, () -> unoService.play(id, "A", card.asJson()));
    }

    @Test public void playCardNonPlayableCard() {
        List<Card> deck = createCustomDeck();
        UUID id = unoService.newMatch(List.of("A", "B"), deck);
        Card card = new NumberCard("Yellow", 3);
        assertThrows(RuntimeException.class, () -> unoService.play(id, "A", card.asJson()));
    }

    private static List<Card> createCustomDeck() {
        List<Card> deck = List.of(new NumberCard("Red", 0),
                new NumberCard("Red", 1),
                new NumberCard("Red", 2),
                new NumberCard("Yellow", 3),
                new NumberCard("Red", 4),
                new NumberCard("Red", 5),
                new NumberCard("Red", 6),
                new NumberCard("Red", 7),
                new NumberCard("Red", 8),
                new NumberCard("Red", 9),
                new SkipCard("Red"),
                new Draw2Card("Red"),
                new ReverseCard("Red"),
                new WildCard());
        return deck;
    }
}
