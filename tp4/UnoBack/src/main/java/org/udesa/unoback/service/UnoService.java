package org.udesa.unoback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.udesa.unoback.model.Card;
import org.udesa.unoback.model.JsonCard;
import org.udesa.unoback.model.Match;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class UnoService {
    @Autowired private Dealer dealer;
    private HashMap<UUID, Match> matches = new HashMap<>();

    public UUID newMatch(List<String> players) {
        UUID newMatchID = UUID.randomUUID();
        matches.put(newMatchID, Match.fullMatch(dealer.fullDeck(), players));
        return newMatchID;
    }

    public List<Card> getPlayerHand(UUID matchId) {
        Match match = matches.get(matchId);
        if (match == null) {
            throw new RuntimeException("There is no match with id: " + matchId);
        }
        return match.playerHand();
    }

    public Card getActiveCard(UUID matchId) {
        Match match = matches.get(matchId);
        if (match == null) {
            throw new RuntimeException("There is no match with id: " + matchId);
        }
        return match.activeCard();
    }

    public void drawCard(UUID matchId, String player) {
        Match match = matches.get(matchId);
        if (match == null) {
            throw new RuntimeException("There is no match with id: " + matchId);
        }
        match.drawCard(player);// runtime exception si el juego termino o no es el turno del jugador
    }

    public void play(UUID matchId, String player, Card card) {
        Match match = matches.get(matchId);
        if (match == null) {
            throw new RuntimeException("There is no match with id: " + matchId);
        }
        match.play(player, card);

    }
}
