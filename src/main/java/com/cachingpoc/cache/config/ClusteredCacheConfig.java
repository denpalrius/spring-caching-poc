package com.cachingpoc.cache.config;

import com.cachingpoc.cache.model.Player;
import org.ehcache.CacheManager;
import org.ehcache.PersistentCacheManager;
import org.ehcache.clustered.client.config.builders.ClusteredResourcePoolBuilder;
import org.ehcache.clustered.client.config.builders.ClusteringServiceConfigurationBuilder;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.terracotta.connection.ConnectionException;

import java.net.URI;

@EnableJpaRepositories(basePackages = "com.cachingpoc.cache.repositories")
@Configuration
@EnableCaching
public class ClusteredCacheConfig {
    private static final String DEFAULT_TSA_PORT = "9410";
    private static final String CACHE_MANAGER_ALIAS = "clustered-cache-poc-manager";
    private static final String CLUSTERED_CACHE_PLAYERS_ALIAS = "players-cache";
    private static final String CLUSTERED_CACHE_TIERED_ALIAS = "tiered-cache";
    private static final String SERVER_RESOURCE_PRIMARY = "primary-server-resource";
    private static final String SERVER_RESOURCE_SECONDARY = "secondary-server-resource";
    private static final String SHARED_RESOURCE_POOL = "resource-pool-a";
    private static final String DEFAULT_SERVER_URI = "terracotta://localhost:" + DEFAULT_TSA_PORT;

    @Bean
    @Primary
    private static CacheManager clusteredCacheManagerBuilder() throws ConnectionException {

        final URI uri = URI.create(DEFAULT_SERVER_URI + "/" + CACHE_MANAGER_ALIAS);

        final CacheManagerBuilder<PersistentCacheManager> clusteredCacheManagerBuilder = CacheManagerBuilder
                .newCacheManagerBuilder()
                .with(ClusteringServiceConfigurationBuilder
                        .cluster(uri).autoCreate()
                        .defaultServerResource(SERVER_RESOURCE_PRIMARY)
                        .resourcePool(SHARED_RESOURCE_POOL, 10, MemoryUnit.MB))
                .withCache(CLUSTERED_CACHE_PLAYERS_ALIAS, CacheConfigurationBuilder.newCacheConfigurationBuilder(
                        Long.class, Player.class, ResourcePoolsBuilder.newResourcePoolsBuilder()
                                .heap(1000, EntryUnit.ENTRIES)
                                .offheap(1, MemoryUnit.MB)
                                .with(ClusteredResourcePoolBuilder.clusteredShared(SHARED_RESOURCE_POOL))))
                .withCache(CLUSTERED_CACHE_TIERED_ALIAS, CacheConfigurationBuilder.newCacheConfigurationBuilder(
                        Long.class, String.class, ResourcePoolsBuilder.newResourcePoolsBuilder()
                                .with(ClusteredResourcePoolBuilder.clusteredShared(SHARED_RESOURCE_POOL))));
//
        System.out.println("]]m ***************************** m [[");
        System.out.println("Building the clustered CacheManager, connecting to : " + uri + ", using " + SERVER_RESOURCE_PRIMARY);
        System.out.println("]]m ***************************** m [[");

        return clusteredCacheManagerBuilder.build(true);
    }

//    @Bean
//    private static CacheManager clusteredCacheManagerBuilder() throws ConnectionException {
//        final URI uri = URI.create(DEFAULT_SERVER_URI + "/" + CACHE_MANAGER_ALIAS);
//
//        CacheManagerBuilder<PersistentCacheManager> clusteredCacheManagerBuilder =
//                CacheManagerBuilder
//                        .newCacheManagerBuilder()
//                        .with(ClusteringServiceConfigurationBuilder
//                                .cluster(uri)
//                                .autoCreate()
//                                .defaultServerResource(SERVER_RESOURCE_PRIMARY)
//                                .resourcePool(SHARED_RESOURCE_POOL, 10, MemoryUnit.MB));
//
//        PersistentCacheManager cacheManager = clusteredCacheManagerBuilder.build(true);

//        CacheConfiguration<String, Player> config = CacheConfigurationBuilder
//                .newCacheConfigurationBuilder(String.class, Player.class, ResourcePoolsBuilder.newResourcePoolsBuilder()
//                        .heap(2, MemoryUnit.MB)
//                        .with(ClusteredResourcePoolBuilder
//                                .clusteredDedicated(SHARED_RESOURCE_POOL, 8, MemoryUnit.MB)
//                        )).withService(ClusteredStoreConfigurationBuilder.withConsistency(Consistency.STRONG))
//                .build();

//        CacheConfiguration<Long, String> config2 = CacheConfigurationBuilder
//                .newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.newResourcePoolsBuilder()
//                        .with(ClusteredResourcePoolBuilder.clusteredShared(SHARED_RESOURCE_POOL)))
//                .withService(ClusteredStoreConfigurationBuilder.withConsistency(Consistency.EVENTUAL))
//                .build();

//        Cache<String, Player> cache = cacheManager.createCache(CLUSTERED_CACHE_PLAYERS_ALIAS, config);
//        Cache<Long, String> cache2 = cacheManager.createCache(CLUSTERED_CACHE_TIERED_ALIAS, config2);
//        cache2.put(42L, "All you need to know!");
//
//        System.out.println("]] x ***********STORED VALUE ****************** x [[");
//        System.out.println(cache2.get(42L));
//
//
//        System.out.println("]] x ***************************** x [[");
//        System.out.println("Building the clustered CacheManager, connecting to : " + uri + ", using " + SERVER_RESOURCE_PRIMARY);
//        System.out.println("]] x ***************************** x [[");
//
//
//        return cacheManager;
//
//    }

    @Bean("customKeyGenerator")
    public KeyGenerator customKeyGenerator() {
        return new CustomKeyGenerator();
    }
}
