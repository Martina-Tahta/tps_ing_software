package uno;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class UnoJugadaTest {
    private ArrayList<String> mazoChico = new ArrayList<>(
            Arrays.asList("r1", "r2", "r3", "r4")
    );

    @Test
    void test00InicioPartida() {
        Partida j = new Partida(0, 0);
        assertEquals("r1", j.iniciarPartida(mazoChico).pit());
    }

    @Test
    void test01InicioPartidaCartasJugadores() {
        Partida j = new Partida(1, 3);
        assertEquals(3, j.iniciarPartida(mazoChico).getCantJugadores());
        assertEquals(1, j.getCantCartasJugador(1));
        assertEquals(1, j.getCantCartasJugador(2));
        assertEquals(1, j.getCantCartasJugador(3));
        assertEquals("r4", j.pit());
    }

    @Test
    void test02PrimeraCartaTirada() {
        Partida j = new Partida(1, 3);
        j.iniciarPartida(mazoChico);
        assertEquals("r1", j.simularTurnoSiguienteJugador().pit());
    }

}
