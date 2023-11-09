package com.flowsphere.plugin.spring.cloud.gateway.cache;

import com.flowsphere.plugin.spring.cloud.gateway.loadbalance.InstantWeight;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class InstantWeightCache {

    public static final String INSTANT_WEIGHT_CACHE_KEY = "instantWeightCacheKey";

    private static final LoadingCache<String, InstantWeight> CAHCE = Caffeine.newBuilder()
            .expireAfterWrite(365 * 100, TimeUnit.DAYS)
            .initialCapacity(2)
            .maximumSize(10)
            .recordStats()
            .build(new CacheLoader<String, InstantWeight>() {

        @Override
        public InstantWeight load(String key) throws Exception {
            return null;
        }

        @Override
        public @NonNull CompletableFuture<InstantWeight> asyncLoad(@NonNull String key, @NonNull Executor executor) {
            return null;
        }
    });


    public static boolean put(String key, InstantWeight ruleEntity) {
        CAHCE.put(key, ruleEntity);

        return Boolean.TRUE;
    }

    public static InstantWeight get(String key) {
        try {
            return CAHCE.get(key);
        } catch (Exception e) {
            log.error("get rule cache error key={}", key, e);
            return null;
        }
    }

    public static boolean clear(String key) {
        CAHCE.invalidate(key);
        return Boolean.TRUE;
    }


}
