package uno;

public class TurnDirectorRight extends TurnDirector {

    @Override
    public UnoPlayer nextPlayer(UnoPlayer currentPlayer) {
        return currentPlayer.getRightPlayer();
    }

    @Override
    public TurnDirector changeTurnDirection() {
        return new TurnDirectorLeft();
    }
}
