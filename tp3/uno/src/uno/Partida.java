package uno;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Partida {
    private int cartasARepartir;
    private String cartaSuperior;
    private HashMap<String, JugadorUno> jugadores = new HashMap<>();
    private ArrayList<String> mazo;
    private String siguienteJugador;

    public Partida(int cartasIniciales, ArrayList<String> nombresJugadores, ArrayList<String> mazo) {
        this.cartasARepartir = cartasIniciales;
        this.instanciarJugadores(nombresJugadores);
        this.mazo = mazo;
        this.siguienteJugador = nombresJugadores.get(0);
    }

    private void instanciarJugadores(ArrayList<String> nombresJugadores) {
        for (int i=0; i<nombresJugadores.size(); i++) {
            this.jugadores.put(nombresJugadores.get(i), new JugadorUno());
        }
    }

    public Partida iniciarPartida() {
        this.cartaSuperior = mazo.remove(0);
        this.repartirCartas();
        return this;
    }

    private void repartirCartaAJugador(JugadorUno jugador) {
        for (int i=0; i<this.cartasARepartir; i++) {
            String carta = this.mazo.remove(0);
            jugador.addCarta(carta);
        }
    }

    private void repartirCartas() {
        this.jugadores
                .values()                           // Collection<Jugador>
                .stream()
                .forEach(this::repartirCartaAJugador);
    }


    public String pit() {
        return this.cartaSuperior;
    }

    public int getCantJugadores() {
        return this.jugadores.size();
    }

    public int getCantCartasJugador(String name) {
        return this.jugadores.get(name).getCantCartas();
    }

    public Partida simularTurnoSiguienteJugador() {
        String carta = this.jugadores.get(this.siguienteJugador).tirarCarta();
        if (carta!=null) {
            this.cartaSuperior = carta;
        }
        return this;
    }
}

