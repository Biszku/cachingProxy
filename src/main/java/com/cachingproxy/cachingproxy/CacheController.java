package com.cachingproxy.cachingproxy;

import java.util.Map;

public class CacheController {
    private Map<String, String> cache;

    public CacheController() {
        this.cache = new java.util.HashMap<>();
    }

    public void put(String key, String value) {
        cache.put(key, value);
    }

    public String get(String key) {
        return cache.get(key);
    }

    public boolean contains(String key) {
        return cache.containsKey(key);
    }

    public void clear() {
        cache.clear();
    }
}
