package uno;

public class ReverseCard extends SpecialColorCard {
    public ReverseCard(String color) {
        super(color);
        this.type = "reverse";
    }

    @Override
    public UnoPlayer applyEffect(UnoGame game) {
        game.setTurnDirector(game.getTurnDirector().changeTurnDirection());
        return game.getTurnDirector().nextPlayer(game.getCurrentPlayerObject());
    }
}
