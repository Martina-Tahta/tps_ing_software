package uno;

public abstract class SpecialColorCard extends Card {
    protected String type;

    public SpecialColorCard(String color) {
        this.color = color;
    }

    @Override
    public boolean canStackOver(Card newCard) {
        return super.canStackOver(newCard) || newCard.equalsSpecialColorCardType(this);
    }

    @Override
    public boolean equalsSpecialColorCardType(SpecialColorCard specialColorCard) {
        return this.type == specialColorCard.type;
    }

    public boolean equals(Card otherCard) {
        return super.equals(otherCard) && otherCard.equalsSpecialColorCardType(this);
    }
}

