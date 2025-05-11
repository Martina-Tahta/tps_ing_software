package uno;

import java.util.ArrayList;

public class Partida {
    private int cartasARepartir;
    private String cartaSuperior;
    private ArrayList<Jugador> jugadores = new ArrayList<>();
    private ArrayList<String> mazo;
    private int siguienteJugador;

    private void repartirCartaAJugador(Jugador jugador) {
        String carta = this.mazo.remove(0);
        jugador.addCarta(carta);
    }

    private void repartirCartas() {
        for (int i=0; i<this.cartasARepartir; i++) {
            this.jugadores.stream().forEachOrdered(jugador -> this.repartirCartaAJugador(jugador));
        }
    }

    private void instanciarJugadores(int cantJugadores) {
        for (int i=0; i<cantJugadores; i++) {
            this.jugadores.add(new Jugador());
        }
    }

    public Partida(int cartasIniciales, int cantJugadores) {
        this.cartasARepartir = cartasIniciales;
        this.instanciarJugadores(cantJugadores);
    }

    public Partida iniciarPartida(ArrayList<String> mazo) {
        //repartir cartas a jugadores segun carasARepartir y cant de jugadores
        this.mazo = mazo;
        this.repartirCartas();
        this.cartaSuperior = mazo.get(0);

        return this;
    }

    public String pit() {
        return this.cartaSuperior;
    }

    public int getCantJugadores() {
        return this.jugadores.size();
    }

    public int getCantCartasJugador(int i) {
        return this.jugadores.get(i-1).getCantCartas();
    }

    public Partida simularTurnoSiguienteJugador() {
        String carta = this.jugadores.get(this.siguienteJugador).tirarCarta();
        if (carta!=null) {
            this.cartaSuperior = carta;
        }
        return this;
    }
}

