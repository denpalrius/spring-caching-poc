package com.mzitoh.ehcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.cachingpoc.ehcache.repositories")
@EntityScan("com.cachingpoc.ehcache")
public class SpringEhcachePocApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringEhcachePocApplication.class, args);
    }
}
