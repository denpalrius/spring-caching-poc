package com.cachingpoc.redis.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class CacheService {
    private final CacheManager cacheManager;

    private Object getCache(String cacheName) {
        return cacheManager.getCache(cacheName).getNativeCache();
    }

    public void puInCache(String cacheName, String key, Object value) {
        cacheManager.getCache(cacheName).put(key, value);
    }

    public Boolean clearCache(String cacheName) {
        try {
            if (cacheName == null) {
                log.error("Cache name {} is invalid", cacheName);
                return false;
            }
            Cache cache = cacheManager.getCache(cacheName); // instanceof RedisCache.  Probably never going to be empty
            if (cache == null) {
                log.error("Cache {} not found", cacheName);
                return false;
            }
            cache.clear();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<String> getCacheNames() {
        return new ArrayList<>(cacheManager.getCacheNames());
    }

    public List<String> clearAllCaches() {
        List<String> cacheNamesResponse = this.getCacheNames();
        for (String cacheName : cacheNamesResponse)
            this.clearCache(cacheName);
        return cacheNamesResponse;
    }

}
