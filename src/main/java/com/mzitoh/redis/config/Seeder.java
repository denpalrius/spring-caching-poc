package com.mzitoh.redis.config;

import com.mzitoh.redis.models.Car;
import com.mzitoh.redis.repositories.CarsRepository;
import com.mzitoh.redis.services.CacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
@RequiredArgsConstructor
public class Seeder {

    private final CarsRepository carsRepository;
    private final CacheService cacheService;

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void seed() {
        List<Car> seedCars = generateCars();

        List<Car> newCars = carsRepository.saveAll(seedCars);
        log.info("Added {} cars to Hibernate DB", newCars.size());

        cacheService.puInCache("cars", "carscontroller_getallcars", newCars);
        log.info("Added {} cars to Redis Cache", newCars.size());

    }

    public List<Car> generateCars() {
        List<Car> cars = new ArrayList<>();

        cars.add(Car.builder().registration("KPD 001").make(Car.CarMake.Mercedes).yom(1972).build());
        cars.add(Car.builder().registration("KDC 002").make(Car.CarMake.Audi).yom(1962).build());
        cars.add(Car.builder().registration("KAP 003").make(Car.CarMake.Peugeot).yom(1970).build());

        return cars;
    }
}
