package com.cachingpoc.redis.repositories;

import com.cachingpoc.redis.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarsRepository extends JpaRepository<Car, Long> {
    Optional<Car> findByRegistration(String registration);
}
