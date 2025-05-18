package uno;

public class Draw2Card extends SpecialColorCard {

    public Draw2Card(String color) {
        super(color);
        this.type = "draw2";
    }

    @Override
    public UnoPlayer applyEffect(UnoGame game) {
        UnoPlayer next = game.getTurnDirector().nextPlayer(game.getCurrentPlayerObject());
        for (int i = 0; i < 2 && !game.getDeck().isEmpty(); i++) {
            next.addCard(game.getDeck().remove(0));
        }
        return game.getTurnDirector().nextPlayer(next);
    }
}
