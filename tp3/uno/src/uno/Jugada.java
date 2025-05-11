package uno;

import java.util.ArrayList;

public class Jugada {
    private int cartasARepartir;
    private String cartaSuperior;

    public Jugada(int cartasIniciales) {
        this.cartasARepartir = cartasIniciales;
    }

    public Jugada iniciarPartida(ArrayList<String> mazo) {
        //repartir cartas a jugadores segun carasARepartir y cant de jugadores
        this.cartaSuperior = mazo.get(0);

        return this;
    }

    public String pit() {
        return this.cartaSuperior;
    }
}
