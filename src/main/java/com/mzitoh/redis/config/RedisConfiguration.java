//package com.workstride.core.config.cache;
//
//import com.workstride.core.config.constants.GlobalConstants;
//import com.workstride.core.config.properties.RedisCacheProperties;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.context.annotation.Profile;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.RedisSerializer;
//import org.springframework.integration.redis.util.RedisLockRegistry;
//import org.springframework.stereotype.Component;
//
//import java.time.Duration;
//import java.util.HashMap;
//import java.util.Map;
//
//
//@RequiredArgsConstructor
//@Component
//public class RedisConfiguration {
//    private final RedisCacheProperties redisCacheProperties;
//    private static final String LOCK_NAME = "redis_lock";
//    public static final String REDIS_PROFILE = "redis";
//
//
//    // TODO: Re-enable this when property is configured in server's application properties
//    // @Bean(name = "RedisCacheServer")
//    // @Primary
//    // public LettuceConnectionFactory redisCacheConnectionFactory() {
//    //     RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration();
//    //     clusterConfiguration.clusterNode(redisCacheProperties.getHost(), redisCacheProperties.getPort());
//
//    //     return new LettuceConnectionFactory(clusterConfiguration);
//    // }
//
//
//   @Bean(name = "RedisCacheServer")
//   @Primary
//   public RedisConnectionFactory redisCacheConnectionFactory() {
////       RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration();
////       String url = "dev-cachemanager.t0jqlp.clustercfg.use1.cache.amazonaws.com";
////       clusterConfiguration.clusterNode(url, 6379);
////       return new LettuceConnectionFactory(clusterConfiguration);
//
//       String url = "127.0.0.1";
//       RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(url, 6379);
//       return new LettuceConnectionFactory(config);
//   }
//
//    @Bean
////    @Profile(REDIS_PROFILE)
//    public RedisTemplate<String, Object> redisTemplate() {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(redisCacheConnectionFactory());
//        template.setKeySerializer(RedisSerializer.string());
//        template.setHashKeySerializer(RedisSerializer.string());
//        template.afterPropertiesSet();
//
//        return template;
//    }
//
//     @Bean
////     @Bean(destroyMethod = "destroy")
//    // @Profile(REDIS_PROFILE)
//    public RedisLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
//        return new RedisLockRegistry(redisConnectionFactory, LOCK_NAME);
//    }
//}
