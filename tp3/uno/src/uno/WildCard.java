package uno;

public class WildCard extends Card {

    public Card setColor(String color) {
        this.color = color;
        return this;
    }

    @Override
    public boolean equals(Card otherCard) {
        return otherCard.isWildCard(this);
    }
    public boolean isWildCard(WildCard wildCard) {
        return true;
    }

    @Override
    public boolean canStackOver(Card newCard) {
        return newCard.acceptColor(this.color);
    }
    protected boolean acceptColor(String otherColor) {return true;}
    protected boolean acceptType(String otherType) {return true;}
    protected boolean acceptNumber(int otherNumber) {return true;}

}


