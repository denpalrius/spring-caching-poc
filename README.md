# Spring Caching POC 

Java app showcasing caching in Spring Boot using Spring Cache API

## Caches implemented
1. ehCache
2. ehCache with Terracota
3. Redis, with distributed lock

## Set up and running the Redis app
1. The app has 3 Spring Boot apps for each cache type. The default app configured to run is the `SpringRedisPocApplication`
2. Ensure you have redis available at `localhost:6379`. Otherwise, configure those in the application.yml
3. Use the postman collection in this repo to run some tests
