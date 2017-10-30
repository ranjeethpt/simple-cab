package com.datarepublic.simplecab.configuration;

import com.datarepublic.simplecab.util.CabTripKeyGenerator;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Created by ranjeethpt on 29/10/17.
 *
 * @author ranjeethpt
 */
@Configuration
@EnableCaching
public class CacheConfiguration {

    public static final String CAB_TRIP_CACHE_NAME = "cabTripCache";

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        simpleCacheManager.setCaches(newArrayList(new ConcurrentMapCache(CAB_TRIP_CACHE_NAME)));
        return simpleCacheManager;
    }

    @Bean
    public KeyGenerator cabTripKeyGenerator() {
        return new CabTripKeyGenerator();
    }
}
