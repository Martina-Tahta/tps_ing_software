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

    public void setRightPlayer(UnoPlayer player) { this.rightPlayer = player;}
    public void setLeftPlayer(UnoPlayer player) { this.leftPlayer = player;}

    public UnoPlayer getRightPlayer() { return this.rightPlayer;}
    public UnoPlayer getLeftPlayer() { return this.leftPlayer;}

    public boolean canThrowCard(Card card) {

        return this.cards.stream().anyMatch(c -> c.equals(card));
        //throw new Error("The current player doesn't have that card.");
    }

    public void throwCard(Card card){
        this.cards.remove(card);
    }
}
