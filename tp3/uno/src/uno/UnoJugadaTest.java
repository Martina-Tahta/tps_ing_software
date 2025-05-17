package uno;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

}
