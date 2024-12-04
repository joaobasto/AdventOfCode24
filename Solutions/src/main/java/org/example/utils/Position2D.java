package org.example.utils;

import java.util.Objects;

/***
 * Class to hold position coordinates in two dimensions.
 * In general, for the puzzles we consider the x-axis and y-axis starting at (0,0) and
 * in the following directions:
 * x: West -> East
 * y: North -> South
 */
public class Position2D {

    private long x;
    private long y;

    public Position2D(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position2D position = (Position2D) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
