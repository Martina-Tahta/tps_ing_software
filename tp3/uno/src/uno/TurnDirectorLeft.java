package uno;

public class TurnDirectorLeft extends TurnDirector {
    @Override
    public UnoPlayer nextPlayer(UnoPlayer currentPlayer) {
        return currentPlayer.getLeftPlayer();
    }

    @Override
    public TurnDirector changeTurnDirection() {
        return new TurnDirectorRight();
    }
}
