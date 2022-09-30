package com.cachingpoc.redis.controllers;

import com.cachingpoc.redis.services.CacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.Cache;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/redis-cache")
@RestController
public class CacheController {
    private final CacheService cacheService;

    @GetMapping("get-cache-names")
    public List<String> clearCache() {
        return cacheService.getCacheNames();
    }

    @GetMapping("clear-cache/{cacheName}")
    public Boolean clearCache(@PathVariable String cacheName) {
        return cacheService.clearCache(cacheName);
    }

    @GetMapping("clear-all-caches")
    public List<String> clearAllCaches() {
        return cacheService.clearAllCaches();
    }
}
