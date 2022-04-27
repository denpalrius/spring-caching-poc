package com.cachingpoc.cache.config;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration.Strategy;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.cachingpoc.cache.repositories")
@Configuration
@EnableCaching
public class CacheConfig {
    private static final long CACHE_TTL_15_MINUTES = 60L * 15L;
    private static final long CACHE_TTI_15_MINUTES = 60L * 15L;

    @Bean
    @Primary
    public org.springframework.cache.CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheManager());
    }

    @Bean
    public net.sf.ehcache.CacheManager ehCacheManager() {
        net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
        config.addCache(cacheConfig("playersCache", 10000, true, true, null, null));
        config.addCache(cacheConfig("myBigMemoryMaxStore", 10000, true, true, null, null));

        return CacheManager.create(config);
    }
    private CacheConfiguration cacheConfig(String name, Integer maxEntries, Boolean copyOnRead, Boolean copyOnWrite, Long timeToLiveSeconds, Long timeToIdleSeconds) {
        CacheConfiguration config = new CacheConfiguration();
        config.setName(name);
        if (maxEntries != null)
            config.setMaxEntriesLocalHeap(maxEntries);
        else
            config.setMaxBytesLocalHeap("50M");
        config.setMemoryStoreEvictionPolicy("LRU");
        config.persistence(new PersistenceConfiguration().strategy(Strategy.LOCALTEMPSWAP));
        config.copyOnRead(copyOnRead);
        config.copyOnWrite(copyOnWrite);
        if (timeToLiveSeconds != null)
            config.timeToLiveSeconds(timeToLiveSeconds);
        if (timeToIdleSeconds != null)
            config.timeToIdleSeconds(timeToIdleSeconds);
        return config;
    }


    @Bean("customKeyGenerator")
    public KeyGenerator keyGenerator() {
        return new CustomKeyGenerator();
    }
}
