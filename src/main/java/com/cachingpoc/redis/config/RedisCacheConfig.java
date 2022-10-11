package com.cachingpoc.redis.config;


import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@EnableCaching
@ComponentScan("com.cachingpoc.redis")
@Configuration
public class RedisCacheConfig {
    private final RedisProperties redisProperties;

    @Bean
    public CacheManager cacheManager() {
        return cacheManager(redisConnectionFactory());

//        return RedisCacheManager.builder(redisConnectionFactory()).enableStatistics().build();
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .prefixCacheNameWith("cache-poc.")
                .entryTtl(Duration.ofHours(1))
                .disableCachingNullValues();

        return RedisCacheManager.builder(connectionFactory)
                .withInitialCacheConfigurations(cacheConfigs())
                .cacheDefaults(config) // ??
                .build();
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(
                redisProperties.getHost(),
                redisProperties.getPort());
        return new JedisConnectionFactory(config);
    }


    @Bean
    public Map<String, RedisCacheConfiguration> cacheConfigs() {
        Map<String, RedisCacheConfiguration> configurations = new HashMap<>();
        configurations.put("test-cache", buildConfigWithTtl("language-pack", 1200L));
        configurations.put("countries-cache", buildConfigWithTtl("language-pack", 1200L));
        configurations.put("cars-cache", buildConfigWithTtl("users", 1200L));
        configurations.put("reports", buildConfigWithTtl("reports", 1200L));

        return configurations;
    }

    private RedisCacheConfiguration buildConfigWithTtl(String name, Long ttl) {
        return RedisCacheConfiguration.defaultCacheConfig()
                .prefixCacheNameWith(name + ".")
                .entryTtl(Duration.ofHours(ttl))
                .disableCachingNullValues();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }
}
