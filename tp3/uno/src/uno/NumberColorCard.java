package uno;

public class NumberColorCard extends Card {
    protected String color;
    private int number;

    public NumberColorCard(String color, int number) {
        this.color = color;
        this.number = number;
    }

    @Override
    public boolean canStackOver(Card other) {
        if (other instanceof NumberColorCard) {
            NumberColorCard o = (NumberColorCard) other;
            return this.color.equals(o.color) || this.number == o.number;
        }
        return other instanceof WildCard;
    }


    @Override
    public boolean equalsNumberColorCardNumber(NumberColorCard numberColorCard) {
        return this.number == numberColorCard.number;
    }

    public boolean equals(Card otherCard) {
        return super.equals(otherCard) && otherCard.equalsNumberColorCardNumber(this);
    }

}
