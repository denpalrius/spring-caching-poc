package com.cachingpoc.redis.controllers;

import com.cachingpoc.redis.models.Planet;
import com.cachingpoc.redis.services.CacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/redis-cache")
@RestController
public class CacheController {
    private final CacheService cacheService;

    @PostMapping("/{key}")
    public Object addToCache(@PathVariable String key, @RequestBody Planet body) {
        cacheService.puInCache("planets", key, body);
        return body;
    }

    @GetMapping("/get-planets/{key}")
    public Object fetchPlanets(@PathVariable String key) {
        return cacheService.getPlanets(key);
    }

    @GetMapping("get-cache-names")
    public List<String> getCacheNames() {
        return cacheService.getCacheNames();
    }

    @GetMapping("clear-cache/{cacheName}")
    public Boolean clearCache(@PathVariable String cacheName) {
        return cacheService.clearCache(cacheName);
    }

    @GetMapping("clear-all-caches")
    public Map<String, List<String>> clearAllCaches() {
        return cacheService.clearAllCaches();
    }
}
