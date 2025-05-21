package uno;

public class NumberColorCard extends Card {
    private int number;

    public NumberColorCard(String color, int number) {
        super(color);
        this.number = number;
    }

    @Override
    public boolean equals(Card otherCard) {
        return otherCard.acceptColor(this.color) && otherCard.equalNumber(this.number);
    }
    protected boolean equalNumber(int otherNumber) {return this.number == otherNumber;}

    @Override
    public boolean canStackOver(Card newCard) {
        return newCard.acceptColor(this.color) || newCard.acceptNumber(this.number);
    }
    protected boolean acceptType(String otherType) {return false;}
    protected boolean acceptNumber(int otherNumber) {return this.equalNumber(otherNumber);}
}
