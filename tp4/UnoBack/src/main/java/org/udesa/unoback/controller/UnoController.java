package org.udesa.unoback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.udesa.unoback.model.Card;
import org.udesa.unoback.model.JsonCard;
import org.udesa.unoback.service.UnoService;

import java.util.List;
import java.util.UUID;

@Controller
public class UnoController {
    @Autowired
    UnoService unoService;

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Internal model error: " + ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntime(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Given parameters error: " + ex.getMessage());
    }

    @PostMapping("newmatch")
    public ResponseEntity newMatch(@RequestParam List<String> players ) {
        return ResponseEntity.ok(unoService.newMatch(players));
    }

    @GetMapping("playerhand/{matchId}")
    public ResponseEntity playerHand( @PathVariable UUID matchId ) {
        return ResponseEntity.ok(unoService.getPlayerHand(matchId).stream().map(each -> each.asJson()));
    }

    @GetMapping("activecard/{matchId}") public ResponseEntity activeCard( @PathVariable UUID matchId ) {
        return ResponseEntity.ok(unoService.getActiveCard(matchId).asJson());
    }

    @PostMapping("draw/{matchId}/{player}")
    public ResponseEntity drawCard( @PathVariable UUID matchId, @PathVariable String player ) {
        unoService.drawCard(matchId, player);
        return ResponseEntity.ok("");
    }

    @PostMapping("play/{matchId}/{player}")
    public ResponseEntity play(@PathVariable UUID matchId, @PathVariable String player, @RequestBody JsonCard card ) {
        Card realCard = card.asCard();

        unoService.play(matchId, player, realCard);
        return ResponseEntity.ok("");
    }
}
