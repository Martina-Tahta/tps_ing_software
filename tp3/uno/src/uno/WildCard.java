package uno;

public class WildCard extends Card {

    public void setColor(String color) {this.color = color;}

    public boolean canStackOver(Card newCard) {
        //return true;
        return super.canStackOver(newCard) || newCard.equalsWildCard(this);
    }

    @Override
    public boolean equalsNumberColorCardNumber(NumberColorCard card) { //para q cuando la carta con numero pregunte si puede apliar esta, lo deje
        return true;
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


