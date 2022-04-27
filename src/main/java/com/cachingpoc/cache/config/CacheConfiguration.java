//package com.cachingpoc.cache.config;
//
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.ehcache.EhCacheCacheManager;
//import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
//import org.springframework.cache.interceptor.KeyGenerator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//
//@EnableJpaRepositories(basePackages = "com.cachingpoc.cache.repositories")
//@EnableCaching
//@Configuration
//public class CacheConfiguration {
//
//    @Bean
//    public CacheManager cacheManager() {
//        return new EhCacheCacheManager(cacheMangerFactory().getObject());
//    }
//
//    @Bean
//    public EhCacheManagerFactoryBean cacheMangerFactory() {
//        EhCacheManagerFactoryBean bean = new EhCacheManagerFactoryBean();
//        bean.setConfigLocation(new ClassPathResource("ehcache.xml"));
//        bean.setShared(true);
//        return bean;
//    }
//
//    @Bean("customKeyGenerator")
//    public KeyGenerator keyGenerator() {
//        return new CustomKeyGenerator();
//    }
//}
