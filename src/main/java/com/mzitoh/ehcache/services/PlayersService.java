package com.mzitoh.ehcache.services;

import com.mzitoh.ehcache.models.Player;
import com.mzitoh.ehcache.repositories.PlayersRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlayersService {
    private static final Logger log = LogManager.getLogger(PlayersService.class);
    @Autowired
    PlayersRepository playersRepository;
    @Autowired
    private CacheManager cacheManager;

    @Cacheable(value = "players-cache", key = "#p0", condition = "#p0!=null")
//    @Cacheable(value = "playersCache", key = "#p0", condition = "#p0!=null")
    public Player getPlayer(String name) {
        log.info("Retrieving from Database for name: " + name);
        Player player = playersRepository.findByName(name);

        return player;
    }

    @Cacheable(value = "players-cache", keyGenerator = "customKeyGenerator")
//    @Cacheable(value = "myBigMemoryMaxStore", keyGenerator = "customKeyGenerator")
    public List<Player> getAllPlayers() {
        log.info("Retrieving all players from Database");
        List<Player> players = playersRepository.findAll();

        return players;
    }

    public Boolean clearAllCaches() {
        log.info("Clearing all caches");

        cacheManager.getCacheNames()
                .forEach(cacheName -> {
                    Cache cache = cacheManager.getCache(cacheName);
                    log.info(cacheName + " cache: " + cache.getNativeCache());
                    cache.clear();
                });

        return true;
    }
}
