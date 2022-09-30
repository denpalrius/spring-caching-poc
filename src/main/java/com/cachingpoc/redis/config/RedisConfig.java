//package com.cachingpoc.redis.config;
//
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
//
//@Configuration
//@EnableCaching
//@ComponentScan("com.cachingpoc.redis")
//@EnableRedisRepositories(basePackages = "com.cachingpoc.redis.repositories")
//@PropertySource("classpath:redis.properties")
//public class RedisConfig {
//    private @Value("${redis.host}") String host;
//    private @Value("${redis.port}") int port;
//
//    @Bean
//    JedisConnectionFactory jedisConnectionFactory() {
//        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
//        return new JedisConnectionFactory(config);
//    }
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate() {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(jedisConnectionFactory());
//        return template;
//    }
//
//    @Bean
//    CacheManager cacheManager() {
//        return new RedisCacheManager(redisTemplate());
//    }
//}
