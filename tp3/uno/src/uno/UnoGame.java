package uno;

import java.util.ArrayList;

// Preguntas
//  - como indica player que no tiene cartas o que va a agarrar del mazo?
//  - el nextTurn se juega aca o desde test se llama a la funcion?
//  - pensando en un juego online, no deberia poder pasar que un jugador tire una carta que no tiene no? osea deberiamos agarrar eso como excepcion? eso implicaria un if
//  - al wildcard se le hace el wild.setColor() no?
//  - habria que hacer que las cartas se creen de cierto color sin dar un string con el nombre no? sino que sea crear cartaRed o algo asi?
//     - hacer esto cuando queremos ponerle un color al wildcard

public class UnoGame {
    private boolean finishedGame = false;
    private Card topCard;
    private UnoPlayer currentPlayer;
    private ArrayList<Card> deck;
    private TurnDirector turnDirector = new TurnDirectorRight();
    private String winner = "No winner yet";

    public UnoGame(int initialAmountCards, ArrayList<String> playerNames, ArrayList<Card> initialDeck) {
        this.deck = initialDeck;
        this.topCard = this.deck.remove(0);
        this.instancePlayers(playerNames, initialAmountCards);
    }

    private void dealACard(UnoPlayer player) {
        player.addCard(this.deck.remove(0));
    }

    public void dealNCards(UnoPlayer player, int n) {
        for (int i = 0; i<n; i++) {
            this.dealACard(player);
        }
    }

    private void instancePlayers(ArrayList<String> playerNames, int initialAmountCards) {
        this.currentPlayer = new UnoPlayer(playerNames.get(0));
        this.dealNCards(this.currentPlayer, initialAmountCards);
        UnoPlayer player = this.currentPlayer;
        for (int i=1; i<playerNames.size(); i++) {

            UnoPlayer nextPlayer = new UnoPlayer(playerNames.get(i));
            this.dealNCards(nextPlayer, initialAmountCards);
            nextPlayer.setLeftPlayer(player);
            player.setRightPlayer(nextPlayer);
            player = nextPlayer;
        }
        player.setRightPlayer(this.currentPlayer);
        this.currentPlayer.setLeftPlayer(player);
    }

    public Card pit() {
        return this.topCard;
    }

    public String getCurrentPlayer() {
        return this.currentPlayer.getName();
    }
    public UnoPlayer getCurrentPlayerObject() {
        return this.currentPlayer;
    }

    public void nextTurn() {
        this.currentPlayer = this.turnDirector.nextPlayer(this.currentPlayer);
    }

    public void changeTurnDirection() {
        this.turnDirector = this.turnDirector.changeTurnDirection();
    }

    private boolean playerCanPlayCard(Card card) {
        return this.currentPlayer.canThrowCard(card) && this.topCard.canStackOver(card);
    }

    public UnoGame play(Card card) {
        if (this.finishedGame) {
            return this;
        }

        if (this.playerCanPlayCard(card)) {
            this.topCard = card;
            if(this.currentPlayer.getAmountCards()==1) {
                this.finishedGame = true;
                this.winner = this.currentPlayer.getName();
                return this;
            }

            if ((card.didPlayerSayUno() && this.currentPlayer.getAmountCards()!=2) || (!card.didPlayerSayUno() && this.currentPlayer.getAmountCards()==2)) {
                this.dealNCards(this.currentPlayer, 2);
            }
            this.currentPlayer.throwCard(card);
            card.applyEffect(this);
            this.topCard.noUno();
        } else {
            this.dealNCards(this.currentPlayer, 2);
            this.nextTurn();
        }
        return this;
    }


    public void drawCard() {
        dealACard(this.currentPlayer);
    }

    public void pass(){
        this.nextTurn();
    }

    public boolean gameEnded() {
        return this.finishedGame;
    }

    public String getWinner() {
        return this.winner;
    }

}

