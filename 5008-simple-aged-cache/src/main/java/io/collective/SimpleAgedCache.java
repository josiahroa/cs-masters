package io.collective;

import java.time.Clock;
import java.time.Instant;
import java.util.HashMap;

public class SimpleAgedCache {
    private Clock clock;
    private HashMap<Object, ExpirableEntry> cache;

    private static class ExpirableEntry {
        private Object value;
        private Instant expirationTime;

        public ExpirableEntry(Object value, Instant expirationTime) {
            this.value = value;
            this.expirationTime = expirationTime;
        }
    }

    private void removeExpiredEntries() {
        for (Object key : this.cache.keySet()) {
            ExpirableEntry entry = this.cache.get(key);
            if (entry.expirationTime.isBefore(this.clock.instant())) {
                this.cache.remove(key);
            }
        }
    }
    
    public SimpleAgedCache(Clock clock) {
        this.clock = clock;
        this.cache = new HashMap<>();
    }

    public SimpleAgedCache() {
        this(Clock.systemUTC());
    }

    public void put(Object key, Object value, int retentionInMillis) {
        cache.put(key, new ExpirableEntry(value, this.clock.instant().plusMillis(retentionInMillis)));
    }

    public boolean isEmpty() {
        return cache.isEmpty();
    }

    public int size() {
        removeExpiredEntries();
        return cache.size();
    }

    public Object get(Object key) {
        ExpirableEntry entry = this.cache.get(key);
        if (entry == null) {
            return null;
        }
        if (entry.expirationTime.isBefore(this.clock.instant())) {
            cache.remove(key);
            return null;
        }
        return entry.value;
    }
}