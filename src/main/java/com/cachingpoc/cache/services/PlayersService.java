package com.cachingpoc.cache.services;

import com.cachingpoc.cache.model.Player;
import com.cachingpoc.cache.repositories.PlayersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlayersService {

    @Autowired
    PlayersRepository playersRepository;

    @Cacheable(value = "playersCache", key = "#p0", condition = "#p0!=null")
//    @Cacheable(value = "myBigMemoryMaxStore", key = "#p0", condition = "#p0!=null")
    public Player getPlayer(String name) {
        System.out.println("Retrieving from Database for name: " + name);
        Player player = playersRepository.findByName(name);

        return player;
    }

    @Cacheable(value = "playersCache", keyGenerator = "customKeyGenerator")
//    @Cacheable(value = "myBigMemoryMaxStore", keyGenerator = "customKeyGenerator")
    public List<Player> getAllPlayers() {
        System.out.println("Retrieving all players from Database");
        List<Player> players = playersRepository.findAll();

        return players;
    }
}
