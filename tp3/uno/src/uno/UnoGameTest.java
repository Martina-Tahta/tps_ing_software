package uno;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


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
    private NumberColorCard b1;
    private NumberColorCard b3;
    private NumberColorCard b4;

    private WildCard wild;
    private SkipCard skipR;
    private SkipCard skipG;
    private SkipCard skipB;

    private ReverseCard reverseR;
    private ReverseCard reverseG;
    private ReverseCard reverseB;

    private Draw2Card draw2R;
    private Draw2Card draw2G;
    private Draw2Card draw2B;

    private ArrayList<Card> smallDeck;
    private ArrayList<String> players2;
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

        b1 = new NumberColorCard("blue", 1);
        b3 = new NumberColorCard("blue", 3);
        b4 = new NumberColorCard("blue", 4);

        wild = new WildCard();

        skipR = new SkipCard("red");
        skipG = new SkipCard("green");
        skipB = new SkipCard("blue");

        reverseR = new ReverseCard("red");
        reverseG = new ReverseCard("green");
        reverseB = new ReverseCard("blue");

        draw2R = new Draw2Card("red");
        draw2G = new Draw2Card("green");
        draw2B = new Draw2Card("blue");

        smallDeck = new ArrayList<>(
                Arrays.asList(r0, r1, g1, y3, r2, g2, g4, r3, g3, r4)
        );

        players2 = new ArrayList<>(
                Arrays.asList("A", "B")
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
        assertTrue(b1.canStackOver(r1));

        assertTrue(r1.canStackOver(reverseR));
        assertTrue(r1.canStackOver(skipR));
        assertTrue(r1.canStackOver(draw2R));
        assertTrue(r1.canStackOver(wild));
        assertTrue(reverseR.canStackOver(r1));
        assertTrue(skipR.canStackOver(r1));
        assertTrue(draw2R.canStackOver(r1));

        assertTrue(reverseR.canStackOver(reverseG));
        assertTrue(skipR.canStackOver(skipB));
        assertTrue(draw2R.canStackOver(draw2G));

        assertTrue(reverseR.canStackOver(skipR));
        assertTrue(skipR.canStackOver(reverseR));
        assertTrue(reverseR.canStackOver(draw2R));
        assertTrue(draw2R.canStackOver(reverseR));
        assertTrue(skipR.canStackOver(draw2R));
        assertTrue(draw2R.canStackOver(skipR));

        assertTrue(reverseR.canStackOver(wild));
        assertTrue(skipR.canStackOver(wild));
        assertTrue(draw2R.canStackOver(wild));
        assertTrue(wild.canStackOver(wild));
        assertTrue(wild.beRed().canStackOver(r1));
        assertTrue(wild.beRed().canStackOver(reverseR));
        assertTrue(wild.beRed().canStackOver(skipR));
        assertTrue(wild.beRed().canStackOver(draw2R));

        assertFalse(r1.canStackOver(g2));
        assertFalse(r1.canStackOver(reverseB));
        assertFalse(reverseB.canStackOver(r1));
        assertFalse(r1.canStackOver(skipB));
        assertFalse(skipB.canStackOver(r1));
        assertFalse(r1.canStackOver(draw2B));
        assertFalse(draw2B.canStackOver(r1));
        assertFalse(reverseR.canStackOver(skipG));
        assertFalse(skipR.canStackOver(draw2B));
        assertFalse(draw2R.canStackOver(reverseG));
        assertFalse(wild.beRed().canStackOver(g1));
    }

    @Test
    void test00_GameStart() {
        UnoGame game = new UnoGame(0, players3, smallDeck);
        assertEquals(r0, game.pit());
    }

    @Test
    void test01_FirstCardPlayed() {
        UnoGame game = new UnoGame(3, players2, smallDeck);
        assertEquals(players2.get(0), game.getCurrentPlayer());
        assertEquals(r1, game.play(r1).pit());
    }

    @Test
    void test02_FirstFullRound() {
        UnoGame game = new UnoGame(3, players3, smallDeck);

        assertEquals(r0, game.pit());
        assertEquals(players3.get(0), game.getCurrentPlayer());

        game.play(r1);
        assertEquals(r1, game.pit());
        assertEquals(players3.get(1), game.getCurrentPlayer());

        game.play(r2);
        assertEquals(r2, game.pit());
        assertEquals(players3.get(2), game.getCurrentPlayer());

        game.play(r3);
        assertEquals(r3, game.pit());
        assertEquals(players3.get(0), game.getCurrentPlayer());
    }

    @Test
    void test03_PlayerPlaysWrongCard() {
        UnoGame game = new UnoGame(3, players2, smallDeck);
        assertEquals(r0, game.pit());
        game.play(g1);
        assertEquals(r0, game.pit());
        assertEquals(players2.get(1), game.getCurrentPlayer());
        game.play(r2);
        assertEquals(players2.get(0), game.getCurrentPlayer());
        game.play(r3);
        assertEquals(players2.get(1), game.getCurrentPlayer());
    }


    @Test
    void test04_RoundChange() {
        ArrayList<Card> deck = new ArrayList<>(
                Arrays.asList(r0, r1, r2, r3, r4, reverseR, g2, y3, g3, r4, g4, y1, y3)
        );
        UnoGame game = new UnoGame(4, players3, deck);
        assertEquals(players3.get(0), game.getCurrentPlayer());
        game.play(r1);
        assertEquals(players3.get(1), game.getCurrentPlayer());
        game.play(reverseR);
        assertEquals(players3.get(0), game.getCurrentPlayer());
        game.play(r2);
        assertEquals(players3.get(2), game.getCurrentPlayer());
    }

    @Test
    void test05_RoundChangeDouble() {
        ArrayList<Card> deck = new ArrayList<>(
                Arrays.asList(r0, r1, reverseG, g1, y4, reverseR, g2, r3, r4, y1, y2, g3, g4)
        );
        UnoGame game = new UnoGame(4, players3, deck);

        assertEquals(r0, game.pit());
        assertEquals(players3.get(0), game.getCurrentPlayer());

        game.play(r1);
        assertEquals(players3.get(1), game.getCurrentPlayer());

        game.play(reverseR);
        assertEquals(players3.get(0), game.getCurrentPlayer());

        game.play(reverseG);
        assertEquals(players3.get(1), game.getCurrentPlayer());

        game.play(g2);
        assertEquals(players3.get(2), game.getCurrentPlayer());
    }

    @Test
    void test06_SkipPlayerRound() {
        ArrayList<Card> deck = new ArrayList<>(
                Arrays.asList(r0, r1, skipG, g1, y2, skipR, g2, r3, y1, y2, g3, y4, r4)
        );
        UnoGame game = new UnoGame(4, players3, deck);

        assertEquals(r0, game.pit());
        assertEquals(players3.get(0), game.getCurrentPlayer());

        game.play(r1);
        assertEquals(players3.get(1), game.getCurrentPlayer());

        game.play(skipR);
        assertEquals(players3.get(0), game.getCurrentPlayer());

        game.play(skipG);
        assertEquals(players3.get(2), game.getCurrentPlayer());

        game.play(g3);
        assertEquals(players3.get(0), game.getCurrentPlayer());
    }

    @Test
    void test07_Draw2Round() {
        ArrayList<Card> deck = new ArrayList<>(
                Arrays.asList(r0, draw2R, g1, g0, g2, y0, y1, y2, r2, b4)
        );
        UnoGame game = new UnoGame(3, players2, deck);

        assertEquals(r0, game.pit());
        assertEquals(players2.get(0), game.getCurrentPlayer());

        game.play(draw2R);
        assertEquals(players2.get(1), game.getCurrentPlayer());

        game.play(r2);
        assertEquals(players2.get(0), game.getCurrentPlayer());
    }

    @Test
    void test08_WildCardRound() {
        ArrayList<Card> deck = new ArrayList<>(
                Arrays.asList(r0, wild, g1, g0, b1, g2, r3, y1, b3, y3, y4)
        );
        UnoGame game = new UnoGame(4, players2, deck);

        assertEquals(r0, game.pit());
        assertEquals(players2.get(0), game.getCurrentPlayer());

        game.play(wild.beGreen());
        assertEquals(players2.get(1), game.getCurrentPlayer());

        game.play(r3);
        assertEquals(players2.get(0), game.getCurrentPlayer());

        game.play(g1);
        assertEquals(players2.get(1), game.getCurrentPlayer());
    }

    @Test
    void test09_PlayerGrabsCardAndThrows() {
        ArrayList<Card> deck = new ArrayList<>(
                Arrays.asList(r0, y1, r3, g0, g2, r3, y1, r4, g3)
        );
        UnoGame game = new UnoGame(3, players2, deck);

        assertEquals(r0, game.pit());
        assertEquals(players2.get(0), game.getCurrentPlayer());

        game.drawCard();
        game.play(r4);
        assertEquals(players2.get(1), game.getCurrentPlayer());

    }

    @Test
    void test10_PlayerGrabsCardAndCantThrow() {
        ArrayList<Card> deck = new ArrayList<>(
                Arrays.asList(r0, y1, b3, g0, g2, r3, y1, y4, g3)
        );
        UnoGame game = new UnoGame(3, players2, deck);

        assertEquals(r0, game.pit());
        assertEquals(players2.get(0), game.getCurrentPlayer());

        game.drawCard();
        game.pass();
        assertEquals(players2.get(1), game.getCurrentPlayer());
        game.play(r3);
    }

    @Test
    void test11_PlayerSaysUnoCorrectly() {
        ArrayList<Card> deck = new ArrayList<>(
                Arrays.asList(r0, r1, b3, g0, g2)
        );
        UnoGame game = new UnoGame(2, players2, deck);

        assertEquals(r0, game.pit());
        assertEquals(players2.get(0), game.getCurrentPlayer());

        game.play(r1.uno());
        assertEquals(r1, game.pit());
        assertEquals(players2.get(1), game.getCurrentPlayer());
    }

    @Test
    void test12_PlayerDoesntSayUno() {
        ArrayList<Card> deck = new ArrayList<>(
                Arrays.asList(r0, r1, b3, g0, g1, y0, y1)
        );
        UnoGame game = new UnoGame(2, players2, deck);

        assertEquals(r0, game.pit());
        assertEquals(players2.get(0), game.getCurrentPlayer());

        game.play(r1);
        assertEquals(r1, game.pit());
        assertEquals(players2.get(1), game.getCurrentPlayer());

        game.play(g1.uno());
        assertEquals(players2.get(0), game.getCurrentPlayer());

        game.play(y1);
        assertEquals(y1, game.pit());
    }

    @Test
    void test13_PlayerThrowsWildCardAndSaysUnoCorrectly() {
        ArrayList<Card> deck = new ArrayList<>(
                Arrays.asList(r0, wild, b3, g0, g2)
        );
        UnoGame game = new UnoGame(2, players2, deck);

        assertEquals(r0, game.pit());
        assertEquals(players2.get(0), game.getCurrentPlayer());

        game.play(wild.beGreen().uno());
        assertEquals(wild, game.pit());
        assertEquals(players2.get(1), game.getCurrentPlayer());
    }

    @Test
    void test14_PlayerWin() {
        ArrayList<Card> deck = new ArrayList<>(
                Arrays.asList(r0, r1, g3, y0, g1, y0, y1)
        );
        UnoGame game = new UnoGame(2, players2, deck);

        assertEquals(r0, game.pit());
        assertEquals(players2.get(0), game.getCurrentPlayer());

        game.play(r1.uno());
        assertEquals(r1, game.pit());
        assertEquals(players2.get(1), game.getCurrentPlayer());

        game.play(g1.uno());
        assertEquals(players2.get(0), game.getCurrentPlayer());

        game.play(g3);
        assertEquals(true, game.gameEnded());
        assertEquals(players2.get(0), game.getWinner());
    }

    @Test
    void test15_ErrorCardThrown() {
        ArrayList<Card> deck = new ArrayList<>(
                Arrays.asList(r0, r1, g3, y0, g1, y0, y1)
        );
        UnoGame game = new UnoGame(3, players2, deck);
        assertEquals(r0, game.pit());

        assertThrows(AssertionError.class, () -> game.play(r4));
        assertEquals(r0, game.pit());
        game.play(r1);
    }

    @Test
    void test16_DoubleErrorCardThrown() {
        ArrayList<Card> deck = new ArrayList<>(
                Arrays.asList(r0, r1, g3, y0, g1, y0, y1)
        );
        UnoGame game = new UnoGame(3, players2, deck);
        assertEquals(r0, game.pit());

        assertThrows(AssertionError.class, () -> game.play(r4));
        assertEquals(r0, game.pit());
        assertThrows(AssertionError.class, () -> game.play(r2));
        assertEquals(r0, game.pit());
        game.play(r1);
    }
}