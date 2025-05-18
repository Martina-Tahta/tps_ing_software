package uno;

public class Draw4Card extends WildCard {

    public UnoPlayer applyEffect(UnoGame game) {
        UnoPlayer next = game.getTurnDirector().nextPlayer(game.getCurrentPlayerObject());
        for (int i = 0; i < 4 && !game.getDeck().isEmpty(); i++) {
            next.addCard(game.getDeck().remove(0));
        }
        return game.getTurnDirector().nextPlayer(next); // Salta turno
    }

    @Override
    public boolean canStackOver(Card newCard) {
        return true;
    }

    @Override
    public boolean equals(Card otherCard) {
        return otherCard instanceof Draw4Card;
    }
}