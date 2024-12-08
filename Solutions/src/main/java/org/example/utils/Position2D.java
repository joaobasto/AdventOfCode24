package org.example.utils;

import java.util.Objects;

/***
 * Class to hold position coordinates in two dimensions.
 * In general, for the puzzles we consider the x-axis and y-axis starting at (0,0) and
 * in the following directions:
 * x: West -> East
 * y: North -> South
 */
public class Position2D implements Copiable<Position2D>{

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

    /**
     * Calculates the new position if, taken this position, we move in the direction of the input direction
     * vector, with a step size defined by stepSize
     * @param directionVector
     * @param stepSize
     * @return
     */
    public Position2D positionAfterMovement(Position2D directionVector, long stepSize) {
        return new Position2D(this.getX() + directionVector.getX()*stepSize,
                this.getY() + directionVector.getY()*stepSize);
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

    @Override
    public Position2D createCopy() {
        return new Position2D(this.getX(), this.getY());
    }
}
