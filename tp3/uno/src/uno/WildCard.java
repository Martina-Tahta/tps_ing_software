package uno;

public class WildCard extends Card {

    public void setColor(String color) {this.color = color;}

    public boolean canStackOver(Card newCard) {
        return super.canStackOver(newCard) || newCard.equalsWildCard(this);
    }

    public boolean equals(Card otherCard) {
        // return super.equals(otherCard) && otherCard.equalsWildCard(this);
        return otherCard.equalsWildCard(this);
    }

    @Override
    public boolean equalsWildCard(WildCard wildCard) {
        return true;
    }

}
