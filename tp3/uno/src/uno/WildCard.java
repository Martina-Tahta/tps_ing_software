package uno;

public class WildCard extends Card {
    public Card beRed() {
        this.color = "red";
        return this;
    }
    public Card beBlue() {
        this.color = "blue";
        return this;
    }
    public Card beGreen() {
        this.color = "green";
        return this;
    }
    public Card beYellow() {
        this.color = "yellow";
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


