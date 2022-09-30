package com.cachingpoc.ehcache.controllers;

import com.cachingpoc.ehcache.models.Player;
import com.cachingpoc.ehcache.services.PlayersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/players")
@RestController
public class PlayersController {
    private final PlayersService playersService;
    Integer allPlayersCount = 0;
    Integer singlePlayerCount = 0;

    @GetMapping
    public List<Player> getAllPlayers() {
        log.info("Fetching all players....." + allPlayersCount++);

        List<Player> players = playersService.getAllPlayers();

        if (players.isEmpty()) throw new PlayerNotFoundException();

        return players;
    }

    @GetMapping(value = "/{name}")
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
