package com.cachingpoc.redis.controllers;

import com.cachingpoc.redis.models.Car;
import com.cachingpoc.redis.repositories.CarsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/cars")
@RestController
public class CarsController {

    private final CarsRepository carsRepository;

    @GetMapping
    @Cacheable(value = "cars", keyGenerator = "customKeyGenerator")
    public List<Car> getAllCars() {
        log.info("Fetching all cars");
        List<Car> cars = carsRepository.findAll();

        log.info("Found {} cars", cars.size());

        return cars;
    }

    @Cacheable(value = "cars", key = "#p0", condition = "#p0!=null")
    @GetMapping("/{id}")
    public Car getCar(@PathVariable Long id) {
        log.info("Fetching car with registration {}", id);
        Optional<Car> car = carsRepository.findById(id);

        if (!car.isPresent()) throw new CarNotFoundException();

        return car.get();
    }

    @PostMapping
    // TODO: Evict cache or update cache list
    @Cacheable(value = "cars", key = "#p0", condition = "#p0!=null")
    public Car addCar(@RequestBody Car car) {
        Car newCar = carsRepository.save(car);

        log.info("Car added: {}", newCar);
        return newCar;
    }

    @CacheEvict(value = "cars", key = "#p0", beforeInvocation = true)
    @CachePut(value = "poc-cache::cars", key = "#car.id")
    @PutMapping("/{id}")
    public Car updateCar(@PathVariable Long id, @RequestBody Car car) {
        Optional<Car> existingCar = carsRepository.findById(id);
        if (!existingCar.isPresent()) throw new CarNotFoundException();
        car.setId(existingCar.get().getId());

        return carsRepository.save(car);
    }

    @CacheEvict(value = "cars", allEntries = true)
    @DeleteMapping("/{id}")
    public boolean deleteCar(@PathVariable Long id) {
        Optional<Car> existingCar = carsRepository.findById(id);
        if (!existingCar.isPresent()) throw new CarNotFoundException();

        carsRepository.deleteById(id);
        return true;
    }

    @CacheEvict(value = "cars", allEntries = true)
    @GetMapping("/clear-all-caches")
    public Object clealAllCaches() {
        log.info("Cache clearing triggered...");
        return null;
    }

    @ExceptionHandler(value = CarNotFoundException.class)
    public ResponseEntity<Object> exception(CarNotFoundException exception) {
        return new ResponseEntity<>("Car not found", HttpStatus.NOT_FOUND);
    }
}
