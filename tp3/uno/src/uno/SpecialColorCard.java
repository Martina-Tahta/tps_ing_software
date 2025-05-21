package uno;

public abstract class SpecialColorCard extends Card {
    protected String type;

    public SpecialColorCard(String color) {
        super(color);
    }

    @Override
    public boolean equals(Card otherCard) {
        return otherCard.equalColor(this.color) && otherCard.equalType(this.type);
    }
    protected boolean equalType(String otherType) {return this.type.equals(otherType);}

    @Override
    public boolean canStackOver(Card newCard) {
        return newCard.acceptColor(this.color) || newCard.acceptType(this.type);
    }
    protected boolean acceptType(String otherType) {return this.equalType(otherType);}
    protected boolean acceptNumber(int otherNumber) {return false;}

}

