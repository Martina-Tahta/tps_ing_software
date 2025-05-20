package uno;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class UnoGameTest {
    private NumberColorCard r0;
    private NumberColorCard r1;
    private NumberColorCard r2;
    private NumberColorCard r3;
    private NumberColorCard r4;
    private NumberColorCard g0;
    private NumberColorCard g1;
    private NumberColorCard g2;
    private NumberColorCard g3;
    private NumberColorCard g4;
    private NumberColorCard y0;
    private NumberColorCard y1;
    private NumberColorCard y2;
    private NumberColorCard y3;
    private NumberColorCard y4;
    private NumberColorCard b0;
    private NumberColorCard b1;
    private NumberColorCard b2;
    private NumberColorCard b3;
    private NumberColorCard b4;

    private WildCard wild;
    private SkipCard skipR;
    private SkipCard skipG;
    private SkipCard skipY;
    private SkipCard skipB;

    private ReverseCard reverseR;
    private ReverseCard reverseG;
    private ReverseCard reverseY;
    private ReverseCard reverseB;

    private Draw2Card draw2R;
    private Draw2Card draw2G;
    private Draw2Card draw2Y;
    private Draw2Card draw2B;

    private ArrayList<Card> smallDeck;
    private ArrayList<String> players3;

    @BeforeEach
    public void setUp() {
        r0 = new NumberColorCard("red", 0);
        r1 = new NumberColorCard("red", 1);
        r2 = new NumberColorCard("red", 2);
        r3 = new NumberColorCard("red", 3);
        r4 = new NumberColorCard("red", 4);

        g0 = new NumberColorCard("green", 0);
        g1 = new NumberColorCard("green", 1);
        g2 = new NumberColorCard("green", 2);
        g3 = new NumberColorCard("green", 3);
        g4 = new NumberColorCard("green", 4);

        y0 = new NumberColorCard("yellow", 0);
        y1 = new NumberColorCard("yellow", 1);
        y2 = new NumberColorCard("yellow", 2);
        y3 = new NumberColorCard("yellow", 3);
        y4 = new NumberColorCard("yellow", 4);

        b0 = new NumberColorCard("blue", 0);
        b1 = new NumberColorCard("blue", 1);
        b2 = new NumberColorCard("blue", 2);
        b3 = new NumberColorCard("blue", 3);
        b4 = new NumberColorCard("blue", 4);

        wild = new WildCard();

        skipR = new SkipCard("red");
        skipG = new SkipCard("green");
        skipY = new SkipCard("yellow");
        skipB = new SkipCard("blue");

        reverseR = new ReverseCard("red");
        reverseG = new ReverseCard("green");
        reverseY = new ReverseCard("yellow");
        reverseB = new ReverseCard("blue");

        draw2R = new Draw2Card("red");
        draw2G = new Draw2Card("green");
        draw2Y = new Draw2Card("yellow");
        draw2B = new Draw2Card("blue");

        smallDeck = new ArrayList<>(
                Arrays.asList(r0, r1, g1, r2, g2, r3, g3, r4, g4)
        );
        players3 = new ArrayList<>(
                Arrays.asList("A", "B", "C")
        );
    }
    @Test
    void testCards() {
        assertTrue(r1.canStackOver(r1));
        assertTrue(r1.canStackOver(r2));
        assertTrue(r1.canStackOver(b1));
        assertTrue(r1.canStackOver(g1));
        assertTrue(r1.canStackOver(y1));

        assertTrue(r1.canStackOver(reverseR));
        assertTrue(r1.canStackOver(skipR));
        assertTrue(r1.canStackOver(draw2R));
        assertTrue(r1.canStackOver(wild));
        assertTrue(reverseR.canStackOver(r1));
        assertTrue(skipR.canStackOver(r1));
        assertTrue(draw2R.canStackOver(r1));
        assertTrue(reverseR.canStackOver(wild));
        assertTrue(skipR.canStackOver(wild));
        assertTrue(draw2R.canStackOver(wild));
        assertTrue(wild.canStackOver(wild));
        wild.setColor("red");
        assertTrue(wild.canStackOver(r1));
        assertTrue(wild.canStackOver(reverseR));

        assertFalse(r1.canStackOver(g2));
        assertFalse(r1.canStackOver(reverseB));
        assertFalse(r1.canStackOver(skipB));
        assertFalse(r1.canStackOver(draw2B));
        assertFalse(wild.canStackOver(g1));
    }

    @Test
    void test00_GameStart() {
        UnoGame game = new UnoGame(0, players3, smallDeck);
        assertEquals(r0, game.pit());
    }

    @Test
    void test01_FirstCardPlayed() {
        UnoGame game = new UnoGame(2, players3, smallDeck);
        assertEquals(players3.get(0), game.getCurrentPlayer());
        assertEquals(r1, game.playNextTurn().pit());
    }

    @Test
    void test02_NextPlayer() {
        UnoGame game = new UnoGame(2, players3, smallDeck);
        game.playNextTurn();
        assertEquals(players3.get(1), game.getCurrentPlayer());
        assertEquals(r2, game.playNextTurn().pit());
    }

    @Test
    void test03_FirstFullRound() {
        UnoGame game = new UnoGame(2, players3, smallDeck);

        assertEquals(r0, game.pit());
        assertEquals(players3.get(0), game.getCurrentPlayer());

        game.playNextTurn();
        assertEquals(r1, game.pit());
        assertEquals(players3.get(1), game.getCurrentPlayer());

        game.playNextTurn();
        assertEquals(r2, game.pit());
        assertEquals(players3.get(2), game.getCurrentPlayer());

        game.playNextTurn();
        assertEquals(r3, game.pit());
        assertEquals(players3.get(0), game.getCurrentPlayer());
    }

    @Test
    void test04_RoundChange() {
        ArrayList<Card> deck = new ArrayList<>(
                Arrays.asList(r0, r1, r2, r3, reverseR, g2, y3, g3, r4, g4, y1)
        );
        UnoGame game = new UnoGame(3, players3, deck);
        assertEquals(players3.get(0), game.getCurrentPlayer());
        game.playNextTurn();
        assertEquals(players3.get(1), game.getCurrentPlayer());
        game.playNextTurn();
        assertEquals(players3.get(0), game.getCurrentPlayer());
        game.playNextTurn();
        assertEquals(players3.get(2), game.getCurrentPlayer());
    }

    @Test
    void test05_RoundChangeDouble() {
        ArrayList<Card> deck = new ArrayList<>(
                Arrays.asList(r0, r1, reverseG, g1, reverseR, g2, r3, y1, y2, g3)
        );
        UnoGame game = new UnoGame(3, players3, deck);

        assertEquals(r0, game.pit());
        assertEquals(players3.get(0), game.getCurrentPlayer());

        game.playNextTurn();
        assertEquals(r1, game.pit());
        assertEquals(players3.get(1), game.getCurrentPlayer());

        game.playNextTurn();
        assertEquals(reverseR, game.pit());
        assertEquals(players3.get(0), game.getCurrentPlayer());

        game.playNextTurn();
        assertEquals(reverseG, game.pit());
        assertEquals(players3.get(1), game.getCurrentPlayer());

        game.playNextTurn();
        assertEquals(g2, game.pit());
        assertEquals(players3.get(2), game.getCurrentPlayer());
    }

    @Test
    void test06_SkipPlayerRound() {
        ArrayList<Card> deck = new ArrayList<>(
                Arrays.asList(r0, r1, skipG, g1, skipR, g2, r3, y1, y2, g3)
        );
        UnoGame game = new UnoGame(3, players3, deck);

        assertEquals(r0, game.pit());
        assertEquals(players3.get(0), game.getCurrentPlayer());

        game.playNextTurn();
        assertEquals(r1, game.pit());
        assertEquals(players3.get(1), game.getCurrentPlayer());

        game.playNextTurn();
        assertEquals(skipR, game.pit());
        assertEquals(players3.get(0), game.getCurrentPlayer());

        game.playNextTurn();
        assertEquals(skipG, game.pit());
        assertEquals(players3.get(2), game.getCurrentPlayer());

        game.playNextTurn();
        assertEquals(g3, game.pit());
        assertEquals(players3.get(0), game.getCurrentPlayer());
    }

    @Test
    void test07_Draw2Round() {
        ArrayList<Card> deck = new ArrayList<>(
                Arrays.asList(r0, draw2R, g1, g0, g2, r3, y1, r3, b4)
        );
        UnoGame game = new UnoGame(2, players3, deck);

        assertEquals(r0, game.pit());
        assertEquals(players3.get(0), game.getCurrentPlayer());

        game.playNextTurn();
        assertEquals(draw2R, game.pit());
        assertEquals(players3.get(1), game.getCurrentPlayer());

        game.playNextTurn();
        assertEquals(r3, game.pit());
        assertEquals(players3.get(2), game.getCurrentPlayer());
    }

    @Test
    void test08_WildCardRound() {
        ArrayList<Card> deck = new ArrayList<>(
                Arrays.asList(r0, wild, g1, g0, g2, r3, y1)
        );
        UnoGame game = new UnoGame(2, players3, deck);

        assertEquals(r0, game.pit());
        assertEquals(players3.get(0), game.getCurrentPlayer());

        game.playNextTurn();
        assertEquals(wild, game.pit());
        assertEquals(players3.get(1), game.getCurrentPlayer());
        game.setWildCardColor("green");

        game.playNextTurn();
        assertEquals(g0, game.pit());
        assertEquals(players3.get(2), game.getCurrentPlayer());

    }

    @Test
    void test09_PlayerHasNoCardsToThrow() {
        ArrayList<Card> deck = new ArrayList<>(
                Arrays.asList(r0, y1, b3, g0, g2, r3, y1, y4, g3)
        );
        UnoGame game = new UnoGame(2, players3, deck);

        assertEquals(r0, game.pit());
        assertEquals(players3.get(0), game.getCurrentPlayer());

        game.playNextTurn();
        assertEquals(r0, game.pit());
        assertEquals(players3.get(1), game.getCurrentPlayer());

        game.playNextTurn();
        assertEquals(g0, game.pit());
        assertEquals(players3.get(2), game.getCurrentPlayer());

        game.playNextTurn();
        assertEquals(g3, game.pit());
        assertEquals(players3.get(0), game.getCurrentPlayer());

    }

}