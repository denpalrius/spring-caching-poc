package com.cachingpoc.redis;

import com.cachingpoc.ehcache.config.CustomKeyGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.cachingpoc.redis.repositories")
@EntityScan("com.cachingpoc.redis")
public class SpringRedisPocApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringRedisPocApplication.class, args);
    }

    @Bean("customKeyGenerator")
    public KeyGenerator keyGenerator() {
        return new CustomKeyGenerator();
    }
}
