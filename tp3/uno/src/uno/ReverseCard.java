package uno;

public class ReverseCard extends SpecialColorCard {
    public ReverseCard(String color) {
        super(color);
        this.type = "reverse";
    }

    @Override
    public void applyEffect(UnoGame game) {
        game.changeTurnDirection();
        game.nextTurn();
    }
}
