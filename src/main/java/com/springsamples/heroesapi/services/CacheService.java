package com.springsamples.heroesapi.services;

import java.util.List;

public interface CacheService {
    void invalidate(String key);
    void invalidate(List<String> keys);
}
