package org.example.Day21;

import java.util.Objects;

public class CacheKey {
    String value;
    int level;


    public CacheKey(String value, int level) {
        this.level = level;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CacheKey cacheKey = (CacheKey) o;
        return level == cacheKey.level && Objects.equals(value, cacheKey.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, level);
    }
}
