package uno;

public abstract class SpecialColorCard extends Card {
    protected String type;

    public SpecialColorCard(String color) {
        super(color);
        System.out.println("Creada carta con color: " + color);
    }

    @Override
    public boolean canStackOver(Card newCard) {
        return this.color.equals(newCard.color) || newCard.equalsSpecialColorCardType(this);
    }

    @Override
    public boolean equalsSpecialColorCardType(SpecialColorCard specialColorCard) {
        return this.type.equals(specialColorCard.type);
    }

    public boolean equals(Card otherCard) {
        return super.equals(otherCard) && otherCard.equalsSpecialColorCardType(this);
    }
}

