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
        players2 = new ArrayList<>(
                Arrays.asList("A", "B")
        );
        players3 = new ArrayList<>(
                Arrays.asList("A", "B", "C")
        );
    }


    @Test
    void test00_GameStart() {
        UnoGame j = new UnoGame(0, players3, smallDeck);
        assertEquals(r0, j.pit());
    }

    @Test
    void test01_FirstCardPlayed() {
        UnoGame j = new UnoGame(2, players3, smallDeck);
        assertEquals(players3.get(0), j.getCurrentPlayer());
        assertEquals(r1, j.playNextTurn().pit());
    }

    @Test
    void test02_NextPlayer() {
        UnoGame j = new UnoGame(2, players3, smallDeck);
        j.playNextTurn();
        assertEquals(players3.get(1), j.getCurrentPlayer());
        assertEquals(r2, j.playNextTurn().pit());
    }

    @Test
    void test03_FirstFullRound() {
        UnoGame j = new UnoGame(2, players3, smallDeck);

        assertEquals(r0, j.pit());
        assertEquals(players3.get(0), j.getCurrentPlayer());

        j.playNextTurn();
        assertEquals(r1, j.pit());
        assertEquals(players3.get(1), j.getCurrentPlayer());

        j.playNextTurn();
        assertEquals(r2, j.pit());
        assertEquals(players3.get(2), j.getCurrentPlayer());

        j.playNextTurn();
        assertEquals(r3, j.pit());
        assertEquals(players3.get(0), j.getCurrentPlayer());
    }

    @Test
    void test04_RoundChange() {
        ArrayList<Card> deck = new ArrayList<>(
                Arrays.asList(r0, r1, r2, r3, reverseR, g2, y3, g3, r4, g4, y1)
        );
        UnoGame j = new UnoGame(3, players3, deck);
        assertEquals(players3.get(0), j.getCurrentPlayer());
        j.playNextTurn();
        assertEquals(players3.get(1), j.getCurrentPlayer());
        j.playNextTurn();
        assertEquals(players3.get(0), j.getCurrentPlayer());
        j.playNextTurn();
        assertEquals(players3.get(2), j.getCurrentPlayer());
    }

    @Test
    void test05_RoundChangeDouble() {
        ArrayList<Card> deck = new ArrayList<>(
                Arrays.asList(r0, r1, reverseG, g1, reverseR, g2, r3, y1, y2, g3)
        );
        UnoGame j = new UnoGame(3, players3, deck);

        assertEquals(r0, j.pit());
        assertEquals(players3.get(0), j.getCurrentPlayer());
        j.playNextTurn();
        assertEquals(r1, j.pit());
        assertEquals(players3.get(1), j.getCurrentPlayer());
        j.playNextTurn();
        assertEquals(reverseR, j.pit());
        assertEquals(players3.get(0), j.getCurrentPlayer());
        j.playNextTurn();
        assertEquals(reverseG, j.pit());
        assertEquals(players3.get(1), j.getCurrentPlayer());
        j.playNextTurn();
        assertEquals(g2, j.pit());
        assertEquals(players3.get(2), j.getCurrentPlayer());
    }

    @Test
    void test06_SameNumberDifferentColor() {
        Card c1 = new NumberColorCard("red", 5);
        Card c2 = new NumberColorCard("blue", 5);

        assertTrue(c1.canStackOver(c2));
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
        game.playNextTurn();
        assertEquals("P2", game.getCurrentPlayer());
        game.playNextTurn();
        assertEquals("P3", game.getCurrentPlayer());
        game.playNextTurn();
        assertEquals("P1", game.getCurrentPlayer());
    }

    @Test
    void test11_ThrowCard() {
//        UnoPlayer player = new UnoPlayer("Player1");
//        Card card = new NumberColorCard("yellow", 9);
//        player.addCard(card);
//
//        Card thrown = player.throwCard();
//        assertEquals(card, thrown);
//        assertThrows(IndexOutOfBoundsException.class, () -> player.throwCard());
    }

    @Test
    void test12_CanStackOverVariousCases() {
        assertTrue(r1.canStackOver(r1));
        assertTrue(r1.canStackOver(r2));
        assertTrue(r1.canStackOver(b1));
        assertTrue(r1.canStackOver(g1));
        assertTrue(r1.canStackOver(y1));


        assertTrue(r1.canStackOver(reverseR));
        assertTrue(r1.canStackOver(skipR));
        assertTrue(r1.canStackOver(draw2R));
        //aca empieza a fallar
        assertTrue(r1.canStackOver(wild));

        assertTrue(g1.canStackOver(reverseG));
        assertTrue(g1.canStackOver(skipG));
        assertTrue(g1.canStackOver(draw2G));
        assertTrue(g1.canStackOver(wild));

        assertTrue(b1.canStackOver(reverseB));
        assertTrue(b1.canStackOver(skipB));
        assertTrue(b1.canStackOver(draw2B));
        assertTrue(b1.canStackOver(wild));

        assertTrue(y1.canStackOver(reverseY));
        assertTrue(y1.canStackOver(skipY));
        assertTrue(y1.canStackOver(draw2Y));
        assertTrue(y1.canStackOver(wild));
    }

    @Test
    void test13_TurnDirectionChangeAffectsTurn() {
        ArrayList<String> players = new ArrayList<>(Arrays.asList("P1", "P2", "P3"));
        ArrayList<Card> deck = new ArrayList<>();
        for (int i = 0; i < 10; i++) deck.add(new NumberColorCard("red", i));
        UnoGame game = new UnoGame(2, players, deck);

        assertEquals("P1", game.getCurrentPlayer());
        game.playNextTurn();
        assertEquals("P2", game.getCurrentPlayer());

        game.changeTurnDirection();
        game.playNextTurn();
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
        UnoGame game = new UnoGame(1, players3, smallDeck);
        String player1 = game.getCurrentPlayer();
        String player2 = game.getCurrentPlayer();

        assertEquals(player1, player2);
    }
}