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
    private ArrayList<String> jugadores = new ArrayList<>(
            Arrays.asList("A", "B", "C")
    );

    @Test
    void test00InicioPartida() {
        Partida j = new Partida(0, jugadores, mazoChico);
        assertEquals("r1", j.iniciarPartida().pit());
    }

    @Test
    void test01InicioPartidaCartasJugadores() {
        Partida j = new Partida(1, jugadores, mazoChico);
        assertEquals(3, j.iniciarPartida().getCantJugadores());
        assertEquals(1, j.getCantCartasJugador("A"));
        assertEquals(1, j.getCantCartasJugador("B"));
        assertEquals(1, j.getCantCartasJugador("C"));
        assertEquals("r1", j.pit());
    }

    @Test
    void test02PrimeraCartaTirada() {
        Partida j = new Partida(1, jugadores, mazoChico);
        j.iniciarPartida();
        assertEquals("r2", j.simularTurnoSiguienteJugador().pit());
    }

}
