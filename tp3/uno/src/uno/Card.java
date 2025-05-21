package uno;

public abstract class Card {
    protected String color;
    protected boolean playerSaidUno = false;

    public Card() {}
    public Card(String color) {
        this.color = color;
    }

    public Card setColor(String color) {return this;}

    public abstract boolean equals(Card otherCard);
    protected boolean equalColor(String otherColor) {return this.color.equals(otherColor);}
    protected boolean equalType(String otherType) {return false;}
    protected boolean equalNumber(int otherNumber) {return false;}
    protected boolean isWildCard(WildCard wildCard){return false;}

    public abstract boolean canStackOver(Card newCard);
    protected boolean acceptColor(String otherColor) {return this.equalColor(otherColor);}
    protected abstract boolean acceptType(String otherType);
    protected abstract boolean acceptNumber(int otherNumber);

    public void applyEffect(UnoGame game) {
        game.nextTurn();
    }

    public Card uno() {
        this.playerSaidUno = true;
        return this;
    }

    public void noUno() {
        this.playerSaidUno = false;
    }

    public boolean didPlayerSayUno() {
        return this.playerSaidUno;
    }
}
