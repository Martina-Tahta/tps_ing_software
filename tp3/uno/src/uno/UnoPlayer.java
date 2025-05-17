package uno;

import java.util.ArrayList;

public class UnoPlayer {
    private String name;
    private ArrayList<Card> cards = new ArrayList<>();
    private UnoPlayer rightPlayer;
    private UnoPlayer leftPlayer;

    public UnoPlayer(String name) {
        this.name = name;
    }
    public String getName() {return  this.name;}
    public void addCard(Card card) {
        this.cards.add(card);
    }
    public int getAmountCards() {
        return this.cards.size();
    }
    public Card throwCard() {
        return this.cards.remove(0);
    }

    public void setRightPlayer(UnoPlayer player) { this.rightPlayer = player;}
    public void setLeftPlayer(UnoPlayer player) { this.leftPlayer = player;}

    public UnoPlayer getRightPlayer() { return this.rightPlayer;}
    public UnoPlayer getLeftPlayer() { return this.leftPlayer;}
}
