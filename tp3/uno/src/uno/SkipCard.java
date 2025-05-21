package uno;

public class SkipCard extends SpecialColorCard {
    public SkipCard(String color) {
        super(color);
        this.type = "skip";
    }

    @Override
    public void applyEffect(UnoGame game) {
        game.nextTurn();
        game.nextTurn();
    }
}
