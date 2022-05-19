package com.cachingpoc.cache.controllers;

import com.cachingpoc.cache.config.CacheLogger;
import com.cachingpoc.cache.model.Player;
import com.cachingpoc.cache.services.PlayersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlayersController {
    private final Logger log = LoggerFactory.getLogger(CacheLogger.class);

    Integer allPlayersCount = 0;
    Integer singlePlayerCount = 0;
    @Autowired
    PlayersService playersService;

    @GetMapping(value = "/players")
    public List<Player> getAllPlayers() {
        log.info("Fetching all players....." + allPlayersCount++);

        List<Player> players = playersService.getAllPlayers();

        if (players.isEmpty()) throw new PlayerNotFoundException();

        return players;
    }

    @GetMapping(value = "players/{name}")
    public Player getPlayer(@PathVariable final String name) {
        log.info("Fetching details for " + name + "....." + singlePlayerCount++);

        Player player = playersService.getPlayer(name);

        if (player == null) throw new PlayerNotFoundException();

        return player;
    }

    @GetMapping(value = "/clear-all-caches")
    public Boolean clearAllCaches() {
        singlePlayerCount = 0;
        allPlayersCount = 0;

        return playersService.clearAllCaches();
    }


    @ExceptionHandler(value = PlayerNotFoundException.class)
    public ResponseEntity<Object> exception(PlayerNotFoundException exception) {
        return new ResponseEntity<>("Player(s) not found", HttpStatus.NOT_FOUND);
    }
}
