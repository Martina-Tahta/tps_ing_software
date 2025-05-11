package uno;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class UnoJugadaTest {
    @Test
    void test00InicioPartida() {
        Jugada j = new Jugada(0);
        ArrayList<String> mazo = new ArrayList<>();
        mazo.add("r1");
        assertEquals("r1", j.iniciarPartida(mazo).pit());

    }
}
