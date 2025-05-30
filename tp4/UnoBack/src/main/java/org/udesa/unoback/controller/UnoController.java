package org.udesa.unoback.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.udesa.unoback.model.JsonCard;

import java.util.List;
import java.util.UUID;

@Controller
public class UnoController {

    @PostMapping("newmatch") public ResponseEntity newMatch(@RequestParam List<String> players ) {}

    @PostMapping("play/{matchId}/{player}") public ResponseEntity play(@PathVariable UUID matchId, @PathVariable String player, @RequestBody JsonCard card ) {}

    @PostMapping("draw/{matchId}/{player}") public ResponseEntity drawCard( @PathVariable UUID matchId, @RequestParam String player ) {}

    @GetMapping("activecard/{matchId}") public ResponseEntity activeCard( @PathVariable UUID matchId ) {}

    @GetMapping("playerhand/{matchId}") public ResponseEntity playerHand( @PathVariable UUID matchId ) {}
}
