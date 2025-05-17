package uno;

import java.util.ArrayList;

public class UnoGame {
    private int initialAmountCards;
    private Card topCard;
    private UnoPlayer currentPlayer;
    private ArrayList<Card> deck;
    private TurnDirector turnDirector = new TurnDirectorRight();

    public UnoGame(int initialAmountCards, ArrayList<String> playerNames, ArrayList<Card> initialDeck) {
        this.initialAmountCards = initialAmountCards;
        this.deck = initialDeck;
        this.startGame(playerNames);
    }

    private UnoGame startGame(ArrayList<String> playerNames) {
        this.topCard = deck.remove(0);
        this.instancePlayers(playerNames);
        return this;
    }

    private void instancePlayers(ArrayList<String> playerNames) {
        this.currentPlayer = new UnoPlayer(playerNames.get(0));
        this.dealCards(this.currentPlayer);
        UnoPlayer player = this.currentPlayer;
        for (int i=1; i<playerNames.size(); i++) {

            UnoPlayer nextPlayer = new UnoPlayer(playerNames.get(i));
            this.dealCards(nextPlayer);
            nextPlayer.setLeftPlayer(player);
            player.setRightPlayer(nextPlayer);
            player = nextPlayer;
        }
        player.setRightPlayer(this.currentPlayer);
        this.currentPlayer.setLeftPlayer(player);
    }

    private void dealCards(UnoPlayer player) {
        for (int i = 0; i<this.initialAmountCards; i++) {
            Card card = this.deck.remove(0);
            player.addCard(card);
        }
    }

    public Card pit() {
        return this.topCard;
    }

    public String getCurrentPlayer() {
        return this.currentPlayer.getName();
    }

    public UnoGame playeNextTurn() {
        Card card = this.currentPlayer.throwCard();
        if (card!=null) {
            this.topCard = card;
        }
        this.currentPlayer = this.turnDirector.nextPlayer(this.currentPlayer);
        return this;
    }

    //    private void repartirCartas() {
//        this.jugadores
//                .values()                           // Collection<Jugador>
//                .stream()
//                .forEach(this::repartirCartaAJugador);
//    }

}

