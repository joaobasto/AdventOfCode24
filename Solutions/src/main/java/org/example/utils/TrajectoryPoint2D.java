package org.example.utils;

import java.util.Objects;

public class TrajectoryPoint2D {

    Position2D position;
    Direction2D direction;

    public TrajectoryPoint2D(Position2D position, Direction2D direction) {
        this.position = position;
        this.direction = direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrajectoryPoint2D that = (TrajectoryPoint2D) o;
        return Objects.equals(position, that.position) && direction == that.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, direction);
    }
}
