package com.springsamples.heroesapi.services;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CacheServiceImpl implements CacheService {

    private final CacheManager manager;

    @Override
    public void invalidate(String key) {
        Cache cache = manager.getCache(key);
        if(cache != null) {
            cache.invalidate();
        }
    }

    @Override
    public void invalidate(List<String> keys) {
        keys.forEach(this::invalidate);
    }
}
