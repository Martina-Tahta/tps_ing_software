package uno;

public class Draw2Card extends SpecialColorCard {
    public Draw2Card(String color) {
        super(color);
        this.type = "draw2";
    }

    @Override
    public void applyEffect(UnoGame game) {
        game.nextTurn();
        game.dealNCards(game.getCurrentPlayerObject(), 2);
    }
}
