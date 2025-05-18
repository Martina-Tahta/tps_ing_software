package uno;

public abstract class Card {
    protected String color;

    public boolean canStackOver(Card newCard) {
        return this.color == newCard.color;
    }
    public boolean equals(Card otherCard) {
        return this.color == otherCard.color;
    }

    protected boolean equalsSpecialColorCardType(SpecialColorCard specialColorCard) {return false;}
    protected boolean equalsNumberColorCardNumber(NumberColorCard numberColorCard){return false;}
    protected boolean equalsWildCard(WildCard wildCard){return false;}
    public UnoPlayer applyEffect(UnoGame game) {
        return game.getTurnDirector().nextPlayer(game.getCurrentPlayerObject());
    }
}

