package com.cachingpoc.cache.config;

import org.ehcache.clustered.client.config.builders.ClusteredResourcePoolBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

import org.ehcache.CacheManager;
import org.ehcache.PersistentCacheManager;
import org.ehcache.clustered.client.config.builders.ClusteringServiceConfigurationBuilder;

import org.ehcache.management.registry.DefaultManagementRegistryConfiguration;

import org.terracotta.connection.ConnectionException;

import java.net.URI;

@EnableJpaRepositories(basePackages = "com.cachingpoc.cache.repositories")
@Configuration
@EnableCaching
public class ClusteredCacheConfig {

    private static final String DEFAULT_TSA_PORT = "9510";
    private static final String TERRACOTTA_URI_ENV = "TERRACOTTA_SERVER_URL";
    private static final String CACHE_MANAGER_ALIAS = "clustered-cache-manager";
    private static final String CACHE_ALIAS = "clustered-cache";
    private static final String SERVER_RESOURCE = "main";
    private static final String SHARED_RESOURCE_POOL = "resource-pool-a";
    private static final String DEFAULT_SERVER_URI_STR = "terracotta://localhost:" + DEFAULT_TSA_PORT;
    private static final String SERVER_URI_STR = System.getenv(TERRACOTTA_URI_ENV) == null ? DEFAULT_SERVER_URI_STR : System.getenv(TERRACOTTA_URI_ENV);


    @Bean
    @Primary
    public CacheManager cacheManager() throws ConnectionException {
//        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
//                .withCache("preConfigured", CacheConfigurationBuilder
//                        .newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(10)))
//                .build();
//
////        cacheManager.init();
//
//        return cacheManager;

        return clusteredCacheManagerBuilder();

    }

//    @Bean
    private static CacheManager clusteredCacheManagerBuilder () throws ConnectionException {
        final URI uri = URI.create(SERVER_URI_STR + "/" + CACHE_MANAGER_ALIAS);
        final CacheManagerBuilder<PersistentCacheManager> clusteredCacheManagerBuilder = CacheManagerBuilder
                .newCacheManagerBuilder()
                .with(ClusteringServiceConfigurationBuilder
                        .cluster(uri).autoCreate()
                        .defaultServerResource(SERVER_RESOURCE)
                        .resourcePool(SHARED_RESOURCE_POOL, 10, MemoryUnit.MB))
                .using(new DefaultManagementRegistryConfiguration()
                        .addTags("my-client-tag", "another-client-tag")
                        .setCacheManagerAlias(CACHE_MANAGER_ALIAS))
                .withCache(CACHE_ALIAS, CacheConfigurationBuilder.newCacheConfigurationBuilder(
                        Long.class,
                        String.class,
                        ResourcePoolsBuilder.newResourcePoolsBuilder()
                                .heap(1000, EntryUnit.ENTRIES)
                                .offheap(1, MemoryUnit.MB)
                                .with(ClusteredResourcePoolBuilder.clusteredShared(SHARED_RESOURCE_POOL))));

        System.out.println("Building the clustered CacheManager, connecting to : " + uri + ", using " + SERVER_RESOURCE);

        return clusteredCacheManagerBuilder.build(true);
    }

    @Bean("customKeyGenerator")
    public KeyGenerator customKeyGenerator() {
        return new CustomKeyGenerator();
    }
}
