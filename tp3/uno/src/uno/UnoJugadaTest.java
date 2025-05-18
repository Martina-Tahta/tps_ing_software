package uno;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    void test00InicioPartida() {
        UnoGame j = new UnoGame(0, players, smallDeck);
        assertEquals(r1, j.pit());
    }

    @Test
    void test01PrimeraCartaTirada() {
        UnoGame j = new UnoGame(1, players, smallDeck);
        assertEquals("A", j.getCurrentPlayer());
        assertEquals(r2, j.playeNextTurn().pit());
    }

    @Test
    void test02SiguienteJugador() {
        UnoGame j = new UnoGame(1, players, smallDeck);
        assertEquals("B", j.playeNextTurn().getCurrentPlayer());
        assertEquals(r3, j.playeNextTurn().pit());
    }

    @Test
    void test03PrimeraRonda() {
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
        assertEquals(r4, j.pit()); // última carta
    }

    @Test
    void test04CambioDeRonda() {
        UnoGame j = new UnoGame(1, players, smallDeck);

        j.playeNextTurn();
        j.playeNextTurn();
        j.playeNextTurn();

        assertEquals("A", j.getCurrentPlayer());
    }


    @Test
    void test05_cambioDeRonda() {
        UnoGame j = new UnoGame(1, players, smallDeck);

        j.playeNextTurn();
        j.playeNextTurn();
        j.playeNextTurn();

        assertEquals("A", j.getCurrentPlayer());
    }

    @Test
    void test06_mismoNumeroDistintoColor() {
        Card c1 = new NumberColorCard("red", 5);
        Card c2 = new NumberColorCard("blue", 5);

        assertTrue(c1.canStackOver(c2));
    }

    @Test
    void test07_lanzarCartaManoVacia() {
        UnoPlayer player = new UnoPlayer("Empty");
        assertThrows(IndexOutOfBoundsException.class, () -> player.throwCard());
    }

    @Test
    void test08_apilarYcompararIgualdad() {
        WildCard wild1 = new WildCard();
        WildCard wild2 = new WildCard();

        assertTrue(wild1.canStackOver(new NumberColorCard("red", 7)));
        assertTrue(wild1.canStackOver(wild2));

        assertTrue(wild1.equals(wild2));

        wild1.setColor("blue");
        assertEquals("blue", wild1.color);
    }

    @Test
    void test09_cambioDeDireccionDelTurno() {
        TurnDirector turnRight = new TurnDirectorRight();
        TurnDirector turnLeft = turnRight.changeTurnDirection();

        assertEquals(TurnDirectorLeft.class, turnLeft.getClass());

        TurnDirector turnRightAgain = turnLeft.changeTurnDirection();
        assertEquals(TurnDirectorRight.class, turnRightAgain.getClass());
    }

    @Test
    void test10_cicloDeTurnos() {
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
    void test11lanzarCarta() {
        UnoPlayer player = new UnoPlayer("Player1");
        Card card = new NumberColorCard("yellow", 9);
        player.addCard(card);

        Card thrown = player.throwCard();
        assertEquals(card, thrown);
        assertThrows(IndexOutOfBoundsException.class, () -> player.throwCard());
    }

}