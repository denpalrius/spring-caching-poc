//package com.cachingpoc.ehcache.config;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.ehcache.CacheManager;
//import org.ehcache.PersistentCacheManager;
//import org.ehcache.clustered.client.config.builders.ClusteredResourcePoolBuilder;
//import org.ehcache.clustered.client.config.builders.ClusteringServiceConfigurationBuilder;
//import org.ehcache.config.builders.CacheConfigurationBuilder;
//import org.ehcache.config.builders.CacheManagerBuilder;
//import org.ehcache.config.builders.ResourcePoolsBuilder;
//import org.ehcache.config.units.MemoryUnit;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.interceptor.KeyGenerator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.terracotta.connection.ConnectionException;
//
//import java.net.URI;
//
//@EnableJpaRepositories(basePackages = "com.cachingpoc.cache.repositories")
//@Configuration
//@EnableCaching
////@Log4j
//public class ClusteredCacheConfig {
//    private static final Logger log = LogManager.getLogger(ClusteredCacheConfig.class);
//
//    private static final String DEFAULT_TSA_PORT = "9410";
//    private static final String CACHE_MANAGER_ALIAS = "clustered-cache-poc-manager";
//    private static final String CLUSTERED_CACHE_PLAYERS_ALIAS = "players-cache";
//    private static final String CLUSTERED_CACHE_TIERED_ALIAS = "tiered-cache";
//    private static final String SERVER_RESOURCE_PRIMARY = "primary-server-resource";
//    private static final String SERVER_RESOURCE_SECONDARY = "secondary-server-resource";
//    private static final String SHARED_RESOURCE_POOL = "resource-pool-a";
////     private static final String DEFAULT_SERVER_URI = "terracotta://localhost:" + DEFAULT_TSA_PORT;
//     private static final String DEFAULT_SERVER_URI = "terracotta://172.31.127.43:" + DEFAULT_TSA_PORT;
////     private static final String DEFAULT_SERVER_URI = "terracotta://172.31.113.236:" + DEFAULT_TSA_PORT;
//
//    @Bean
//    @Primary
//    private static CacheManager clusteredCacheManagerBuilder() throws ConnectionException {
//
////        List<InetSocketAddress> servers = new ArrayList<>();
////        // servers.add(new InetSocketAddress("172.31.113.43", 9410));
////        servers.add(new InetSocketAddress("localhost", 9510));
////        servers.add(new InetSocketAddress("172.31.113.236", 9410));
////        //        .cluster(servers, CACHE_MANAGER_ALIAS)
////
////        String uri1 = "terracotta://172.31.127.43:9410";
////        String uri2 = "terracotta://172.31.113.236:9410";
//
////        final URI uri = URI.create(uri1);
////        final URI uri = URI.create(uri1 + "," + uri2);
//
////        final URI uri = URI.create(DEFAULT_SERVER_URI + "/" + CACHE_MANAGER_ALIAS);
//
////        final URI uri = URI.create("terracotta://172.31.127.43:9410,172.31.113.236:9410");
//        final URI uri = URI.create("terracotta://172.31.127.43:9410");
////        final URI uri = URI.create("terracotta://localhost:9410/" + CACHE_MANAGER_ALIAS);
//
////        CacheManagerBuilder<PersistentCacheManager> clusteredCacheManagerBuilder2 =
////                CacheManagerBuilder.newCacheManagerBuilder()
////                        .with(ClusteringServiceConfigurationBuilder
////                                .cluster(uri)
////                                .autoCreateOnReconnect(c -> c));
////        PersistentCacheManager cacheManager = clusteredCacheManagerBuilder2.build(true);
//
////        log.info("********************");
////        log.info("Connected to " + uri);
////
////        return cacheManager;
//
//
//
//        CacheManagerBuilder<PersistentCacheManager> clusteredCacheManagerBuilder =
//                CacheManagerBuilder.newCacheManagerBuilder()
//                        .with(ClusteringServiceConfigurationBuilder
//                                .cluster(uri)
//                                .autoCreateOnReconnect(server -> server
//                                .defaultServerResource("primary-server-resource")
//                                .resourcePool("resource-pool-a", 8, MemoryUnit.MB, "secondary-server-resource")
//                                .resourcePool("resource-pool-b", 10, MemoryUnit.MB)))
//                        .withCache("clustered-cache", CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
//                                ResourcePoolsBuilder.newResourcePoolsBuilder()
//                                        .with(ClusteredResourcePoolBuilder.clusteredDedicated("primary-server-resource", 8, MemoryUnit.MB))))
//                        .withCache("shared-cache-1", CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
//                                ResourcePoolsBuilder.newResourcePoolsBuilder()
//                                        .with(ClusteredResourcePoolBuilder.clusteredShared("resource-pool-a"))))
//                        .withCache("shared-cache-2", CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
//                                ResourcePoolsBuilder.newResourcePoolsBuilder()
//                                        .with(ClusteredResourcePoolBuilder.clusteredShared("resource-pool-a"))));
//        PersistentCacheManager cacheManager = clusteredCacheManagerBuilder.build(true);
//
//        log.info("********************");
//        log.info("Connected to " + uri);
//
//        return cacheManager;
//
//
//
//
////        final CacheManagerBuilder<PersistentCacheManager> clusteredCacheManagerBuilder = CacheManagerBuilder
////                .newCacheManagerBuilder()
////                .with(ClusteringServiceConfigurationBuilder
////                        .cluster(uriX)
////                        .autoCreateOnReconnect()
////                        .defaultServerResource(SERVER_RESOURCE_PRIMARY)
////                        .resourcePool(SHARED_RESOURCE_POOL, 10, MemoryUnit.MB))
////                .withCache(CLUSTERED_CACHE_PLAYERS_ALIAS, CacheConfigurationBuilder.newCacheConfigurationBuilder(
////                        Long.class, Player.class, ResourcePoolsBuilder.newResourcePoolsBuilder()
////                                .heap(1000, EntryUnit.ENTRIES)
////                                .offheap(1, MemoryUnit.MB)
////                                .with(ClusteredResourcePoolBuilder.clusteredShared(SHARED_RESOURCE_POOL))))
////                .withCache(CLUSTERED_CACHE_TIERED_ALIAS, CacheConfigurationBuilder.newCacheConfigurationBuilder(
////                        Long.class, String.class, ResourcePoolsBuilder.newResourcePoolsBuilder()
////                                .with(ClusteredResourcePoolBuilder.clusteredShared(SHARED_RESOURCE_POOL))));
////
////        System.out.println("[] ***************************** []");
////        System.out.println("Building the clustered CacheManager, connecting to : '" + uriX + "', using " + SERVER_RESOURCE_PRIMARY);
////        System.out.println("[] ***************************** []");
//
////        return clusteredCacheManagerBuilder.build(true);
//    }
//
//
//    @Bean("customKeyGenerator")
//    public KeyGenerator customKeyGenerator() {
//        return new CustomKeyGenerator();
//    }
//}
