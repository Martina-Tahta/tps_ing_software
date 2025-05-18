package uno;

public class SkipCard extends SpecialColorCard {

    public SkipCard(String color) {
        super(color);
        this.type = "skip";
    }

    @Override
    public UnoPlayer applyEffect(UnoGame game) {
        UnoPlayer skipped = game.getTurnDirector().nextPlayer(game.getCurrentPlayerObject());
        return game.getTurnDirector().nextPlayer(skipped);
    }
}
