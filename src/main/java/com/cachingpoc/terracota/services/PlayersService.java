package com.cachingpoc.terracota.services;

import com.cachingpoc.terracota.models.Player;
import com.cachingpoc.terracota.repositories.PlayersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;


@Log4j2
@RequiredArgsConstructor
@Component
public class PlayersService {
    private final PlayersRepository playersRepository;
    private final CacheManager cacheManager;

    @Cacheable(value = "palyers-tc-cache", key = "#p0", condition = "#p0!=null")
    public Player getPlayer(String name) {
        log.info("Retrieving from Database for name: " + name);
        return playersRepository.findByName(name);

    }

    @Cacheable(value = "players-tc-cache", keyGenerator = "customKeyGenerator")
    public List<Player> getAllPlayers() {
        log.info("Retrieving all players from Database");
        return playersRepository.findAll();
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
