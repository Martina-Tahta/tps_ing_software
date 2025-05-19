package uno;

import java.util.ArrayList;

public class UnoGame {
    private boolean finishedGame = false;
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
        this.dealNCards(this.currentPlayer, this.initialAmountCards);
        UnoPlayer player = this.currentPlayer;
        for (int i=1; i<playerNames.size(); i++) {

            UnoPlayer nextPlayer = new UnoPlayer(playerNames.get(i));
            this.dealNCards(nextPlayer, this.initialAmountCards);
            nextPlayer.setLeftPlayer(player);
            player.setRightPlayer(nextPlayer);
            player = nextPlayer;
        }
        player.setRightPlayer(this.currentPlayer);
        this.currentPlayer.setLeftPlayer(player);
    }

    private void dealACard(UnoPlayer player) {
        Card card = this.deck.remove(0);
        player.addCard(card);
    }

    public void dealNCards(UnoPlayer player, int n) {
        for (int i = 0; i<n; i++) {
            this.dealACard(player);
        }
    }

    public Card pit() {
        return this.topCard;
    }

    public String getCurrentPlayer() {
        return this.currentPlayer.getName();
    }

    public void nextTurn() {
        this.currentPlayer = this.turnDirector.nextPlayer(this.currentPlayer);
    }

    public UnoGame playNextTurn() {
        Card card = this.currentPlayer.throwCard(this.topCard, this);
        if (this.currentPlayer.getAmountCards()==0) {
            this.finishedGame = true;
            return this;
        }
        if (card!=null) {
            this.topCard = card;
            card.applyEffect(this);
        }
        else {
            this.nextTurn();
        }
        return this;
    }

    public void changeTurnDirector() {
        this.turnDirector = this.turnDirector.changeTurnDirection();
    }

    public UnoPlayer getCurrentPlayerObject() {
        return this.currentPlayer;
    }

    public void changeTurnDirection() {
        this.turnDirector = this.turnDirector.changeTurnDirection();
    }

    public void setWildCardColor(String color) {
        this.topCard.setColor(color);

    }

    public boolean gameEnded() {
        return this.finishedGame;
    }

}

