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
    public Card throwCard(Card topCard, UnoGame game) {
        for (int i = 0; i<this.getAmountCards(); i++) {
            if (topCard.canStackOver(this.cards.get(i))) {
                return this.cards.remove(i);
            }
        }
        game.dealNCards(this, 1);
        if (topCard.canStackOver(this.cards.getLast())) {
            return this.cards.removeLast();
        }
        return null;
    }

    public void setRightPlayer(UnoPlayer player) { this.rightPlayer = player;}
    public void setLeftPlayer(UnoPlayer player) { this.leftPlayer = player;}

    public UnoPlayer getRightPlayer() { return this.rightPlayer;}
    public UnoPlayer getLeftPlayer() { return this.leftPlayer;}
}
