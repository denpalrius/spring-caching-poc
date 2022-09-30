package com.cachingpoc.terracota;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.cachingpoc.terracota.repositories")
@EntityScan("com.cachingpoc.terracota")
public class SpringTerracotaPocApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringTerracotaPocApplication.class, args);
    }
}
