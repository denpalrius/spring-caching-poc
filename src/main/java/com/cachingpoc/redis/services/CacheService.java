package com.cachingpoc.redis.services;

import com.cachingpoc.redis.config.CacheNames;
import com.cachingpoc.redis.models.Planet;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Log4j2
@RequiredArgsConstructor
@Service
public class CacheService {
    private final CacheManager cacheManager;

    private Object getCache(String cacheName) {
        return cacheManager.getCache(cacheName).getNativeCache();
    }

    public Object getPlanets(String key) {
//        ValueWrapper planetValue = Objects.requireNonNull(cacheManager.getCache("planets")).get(key);
//        return planetValue.get();

        Planet planet =  cacheManager.getCache(CacheNames.PLANETS.getName()).get(key, Planet.class);
        return planet;
    }

    public void puInCache(String cacheName, String key, Object value) {
        cacheManager.getCache(cacheName).put(key, value);
    }

    public Boolean clearCache(String key) {
        try {
            if (key == null) {
                log.error("Cache name {} is invalid", key);
                return false;
            }
            Cache cache = cacheManager.getCache(key); // instanceof RedisCache.  Probably never going to be empty
            if (cache == null) {
                log.error("Cache {} not found", key);
                return false;
            }
            cache.invalidate();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<String> getCacheNames() {
        return new ArrayList<>(cacheManager.getCacheNames());
    }

    public Map<String, List<String>> clearAllCaches() {
        List<String> cacheNames = this.getCacheNames();

        for (String cacheName : cacheNames) {
            this.clearCache(cacheName);
        }

        Map<String, List<String>> map = new HashMap<>();
        map.put("caches cleared", cacheNames);
        return map;
    }

}
