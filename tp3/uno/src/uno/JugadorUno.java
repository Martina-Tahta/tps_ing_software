package uno;

import java.util.ArrayList;

public class JugadorUno {
    private ArrayList<String> cartas = new ArrayList<>();



    public void addCarta(String carta) {
        this.cartas.add(carta);
    }

    public int getCantCartas() {
        return this.cartas.size();
    }

    public String tirarCarta() {
        return this.cartas.remove(0);
    }
}
