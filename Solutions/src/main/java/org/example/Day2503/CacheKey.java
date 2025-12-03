package org.example.Day2503;

import java.util.List;
import java.util.Objects;

public class CacheKey {
    private int position;
    private List<Integer> state;

    public CacheKey(int position, List<Integer> state) {
        this.position = position;
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CacheKey cacheKey = (CacheKey) o;
        return position == cacheKey.position && Objects.equals(state, cacheKey.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, state);
    }
}
