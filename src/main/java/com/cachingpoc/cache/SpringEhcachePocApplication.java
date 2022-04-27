package com.cachingpoc.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringEhcachePocApplication {
    private static final Logger log = LoggerFactory.getLogger(SpringEhcachePocApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringEhcachePocApplication.class, args);
    }
}
