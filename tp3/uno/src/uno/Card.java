package uno;

public abstract class Card {
    protected String color;

    public Card() {}
    public Card(String color) {
        this.color = color;
    }

    public boolean canStackOver(Card newCard) {
        System.out.println("Color actual: " + this.color);
        return this.color.equals(newCard.color);
    }
    public boolean equals(Card otherCard) {
        return this.color.equals(otherCard.color);
    }

    public void setColor(String color) {}

    protected boolean equalsSpecialColorCardType(SpecialColorCard specialColorCard) {return false;}
    protected boolean equalsNumberColorCardNumber(NumberColorCard numberColorCard){return false;}
    protected boolean equalsWildCard(WildCard wildCard){return false;}
    public void applyEffect(UnoGame game) {
        game.nextTurn();
    }
}

