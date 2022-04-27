package com.cachingpoc.cache.controllers;

import com.cachingpoc.cache.model.Player;
import com.cachingpoc.cache.services.PlayersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/players")
public class PlayersController {
    Integer allPlayersCount = 0;
    Integer singlePlayerCount = 0;
    @Autowired
    PlayersService playersService;

    @GetMapping()
    public List<Player> getAllPlayers() {
        System.out.println("Fetching all players....." + allPlayersCount++);

        List<Player> players = playersService.getAllPlayers();

        if (players.isEmpty()) throw new PlayerNotFoundException();

        return players;
    }

    @GetMapping(value = "/{name}")
    public Player getPlayer(@PathVariable final String name) {
        System.out.println("Fetching details for " + name + "....." + singlePlayerCount++);

        Player player = playersService.getPlayer(name);

        if (player == null) throw new PlayerNotFoundException();

        return player;
    }


    @ExceptionHandler(value = PlayerNotFoundException.class)
    public ResponseEntity<Object> exception(PlayerNotFoundException exception) {
        return new ResponseEntity<>("Player(s) not found", HttpStatus.NOT_FOUND);
    }
}
