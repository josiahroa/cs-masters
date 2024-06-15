package io.collective;

import java.time.Clock;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class SimpleAgedCache {
    private final Clock clock;
    private final Map<Object, CacheEntry> cache = new HashMap<>();

    public SimpleAgedCache(Clock clock) {
        this.clock = clock;
    }

    public SimpleAgedCache() {
        this.clock = Clock.systemDefaultZone();
    }

    public void put(Object key, Object value, int retentionInMillis) {
        Instant expirationTime = clock.instant().plusMillis(retentionInMillis);
        cache.put(key, new CacheEntry(value, expirationTime));
    }

    public boolean isEmpty() {
        clearCache();
        return cache.isEmpty();
    }

    public int size() {
        clearCache();
        return cache.size();
    }

    public Object get(Object key) {
        clearCache();
        CacheEntry entry = cache.get(key);

        if (entry != null) {
            return entry.value;
        } else {
            return null;
        }
    }

    private void clearCache() {
        Instant instant = clock.instant();
        cache.values().removeIf(entry -> entry.expirationTime.isBefore(instant));
    }

    private static class CacheEntry {
        private final Object value;
        private final Instant expirationTime;

        CacheEntry(Object value, Instant expirationTime) {
            this.value = value;
            this.expirationTime = expirationTime;
        }
    }
}