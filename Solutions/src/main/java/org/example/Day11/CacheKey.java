package org.example.Day11;

import java.util.Objects;

public class CacheKey {

    private final long number;
    private final long currentBlink;

    public CacheKey(long number, long currentBlink) {
        this.number = number;
        this.currentBlink = currentBlink;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CacheKey cacheKey = (CacheKey) o;
        return number == cacheKey.number && currentBlink == cacheKey.currentBlink;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, currentBlink);
    }
}
