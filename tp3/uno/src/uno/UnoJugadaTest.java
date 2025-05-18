package uno;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class UnoJugadaTest {
    private Card r1;
    private Card r2;
    private Card r3;
    private Card r4;
    private ArrayList<Card> smallDeck;
    private ArrayList<String> players;

    @BeforeEach
    public void setUp() {
        r1 = new NumberColorCard("red", 1);
        r2 = new NumberColorCard("red", 2);
        r3 = new NumberColorCard("red", 3);
        r4 = new NumberColorCard("red", 4);

        smallDeck = new ArrayList<>(
                Arrays.asList(r1, r2, r3, r4)
        );
        players = new ArrayList<>(
                Arrays.asList("A", "B", "C")
        );
    }


    @Test
    void test00_GameStart() {
        UnoGame j = new UnoGame(0, players, smallDeck);
        assertEquals(r1, j.pit());
    }

    @Test
    void test01_FirstCardPlayed() {
        UnoGame j = new UnoGame(1, players, smallDeck);
        assertEquals("A", j.getCurrentPlayer());
        assertEquals(r2, j.playeNextTurn().pit());
    }

    @Test
    void test02_NextPlayer() {
        UnoGame j = new UnoGame(1, players, smallDeck);
        assertEquals("B", j.playeNextTurn().getCurrentPlayer());
        assertEquals(r3, j.playeNextTurn().pit());
    }

    @Test
    void test03_FirstRound() {
        UnoGame j = new UnoGame(1, players, smallDeck);

        assertEquals("A", j.getCurrentPlayer());
        assertEquals(r1, j.pit());

        j.playeNextTurn();
        assertEquals("B", j.getCurrentPlayer());
        assertEquals(r2, j.pit());

        j.playeNextTurn();
        assertEquals("C", j.getCurrentPlayer());
        assertEquals(r3, j.pit());

        j.playeNextTurn();
        assertEquals("A", j.getCurrentPlayer());
        assertEquals(r4, j.pit()); // last card
    }

    @Test
    void test04_RoundChange() {
        UnoGame j = new UnoGame(1, players, smallDeck);

        j.playeNextTurn();
        j.playeNextTurn();
        j.playeNextTurn();

        assertEquals("A", j.getCurrentPlayer());
    }

    @Test
    void test05_RoundChangeDuplicate() {
        UnoGame j = new UnoGame(1, players, smallDeck);

        j.playeNextTurn();
        j.playeNextTurn();
        j.playeNextTurn();

        assertEquals("A", j.getCurrentPlayer());
    }

    @Test
    void test06_SameNumberDifferentColor() {
        Card c1 = new NumberColorCard("red", 5);
        Card c2 = new NumberColorCard("blue", 5);

        assertTrue(c1.canStackOver(c2));
    }

    @Test
    void test07_ThrowCardEmptyHand() {
        UnoPlayer player = new UnoPlayer("Empty");
        assertThrows(IndexOutOfBoundsException.class, () -> player.throwCard());
    }

    @Test
    void test08_StackAndEquality() {
        WildCard wild1 = new WildCard();
        WildCard wild2 = new WildCard();

        assertTrue(wild1.canStackOver(new NumberColorCard("red", 7)));
        assertTrue(wild1.canStackOver(wild2));

        assertTrue(wild1.equals(wild2));

        wild1.setColor("blue");
        assertEquals("blue", wild1.color);
    }

    @Test
    void test09_ChangeTurnDirection() {
        TurnDirector turnRight = new TurnDirectorRight();
        TurnDirector turnLeft = turnRight.changeTurnDirection();

        assertEquals(TurnDirectorLeft.class, turnLeft.getClass());

        TurnDirector turnRightAgain = turnLeft.changeTurnDirection();
        assertEquals(TurnDirectorRight.class, turnRightAgain.getClass());
    }

    @Test
    void test10_TurnCycle() {
        ArrayList<String> players = new ArrayList<>(Arrays.asList("P1", "P2", "P3"));
        ArrayList<Card> deck = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            deck.add(new NumberColorCard("red", i));
        }
        UnoGame game = new UnoGame(2, players, deck);

        assertEquals("P1", game.getCurrentPlayer());
        game.playeNextTurn();
        assertEquals("P2", game.getCurrentPlayer());
        game.playeNextTurn();
        assertEquals("P3", game.getCurrentPlayer());
        game.playeNextTurn();
        assertEquals("P1", game.getCurrentPlayer());
    }

    @Test
    void test11_ThrowCard() {
        UnoPlayer player = new UnoPlayer("Player1");
        Card card = new NumberColorCard("yellow", 9);
        player.addCard(card);

        Card thrown = player.throwCard();
        assertEquals(card, thrown);
        assertThrows(IndexOutOfBoundsException.class, () -> player.throwCard());
    }

    @Test
    void test12_CanStackOverVariousCases() {
        Card red5 = new NumberColorCard("red", 5);
        Card blue5 = new NumberColorCard("blue", 5);
        Card red7 = new NumberColorCard("red", 7);
        Card green7 = new NumberColorCard("green", 7);
        Card yellow3 = new NumberColorCard("yellow", 3);
        WildCard wild = new WildCard();

        assertTrue(red5.canStackOver(blue5));
        assertTrue(red5.canStackOver(red7));
        assertFalse(red5.canStackOver(yellow3));
        assertTrue(wild.canStackOver(red7));
        assertTrue(wild.canStackOver(wild));
    }

    @Test
    void test13_TurnDirectionChangeAffectsTurn() {
        ArrayList<String> players = new ArrayList<>(Arrays.asList("P1", "P2", "P3"));
        ArrayList<Card> deck = new ArrayList<>();
        for (int i = 0; i < 10; i++) deck.add(new NumberColorCard("red", i));
        UnoGame game = new UnoGame(2, players, deck);

        assertEquals("P1", game.getCurrentPlayer());
        game.playeNextTurn();
        assertEquals("P2", game.getCurrentPlayer());

        game.changeTurnDirection();
        game.playeNextTurn();
        assertEquals("P1", game.getCurrentPlayer());
    }

    @Test
    void test14_EqualsWildCard() {
        WildCard w1 = new WildCard();
        WildCard w2 = new WildCard();
        NumberColorCard n1 = new NumberColorCard("red", 7);

        assertTrue(w1.equals(w2));
        assertFalse(w1.equals(n1));
    }

    @Test
    void test15_GetCurrentPlayerDoesNotChangeState() {
        UnoGame game = new UnoGame(1, players, smallDeck);
        String player1 = game.getCurrentPlayer();
        String player2 = game.getCurrentPlayer();

        assertEquals(player1, player2);
    }
}