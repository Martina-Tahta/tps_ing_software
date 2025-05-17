package uno;

public abstract class TurnDirector {
    public abstract UnoPlayer nextPlayer(UnoPlayer currentPlayer);
    public abstract TurnDirector changeTurnDirection();
}

