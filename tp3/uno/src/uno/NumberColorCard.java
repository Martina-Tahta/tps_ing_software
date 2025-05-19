package uno;

public class NumberColorCard extends Card {
    protected String color;
    private int number;

    public NumberColorCard(String color, int number) {
        this.color = color;
        this.number = number;
    }

    @Override
    public boolean canStackOver(Card newCard) {
        return super.canStackOver(newCard) || newCard.equalsNumberColorCardNumber(this);
    }


    @Override
    public boolean equalsNumberColorCardNumber(NumberColorCard numberColorCard) {
        return this.number == numberColorCard.number;
    }

    public boolean equals(Card otherCard) {
        return super.equals(otherCard) && otherCard.equalsNumberColorCardNumber(this);
    }

}
