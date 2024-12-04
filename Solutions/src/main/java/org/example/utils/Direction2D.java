package org.example.utils;

import java.util.Map;

public enum Direction2D {

    NORTH(0, -1),
    EAST(1, 0),
    SOUTH(0, 1),
    WEST( -1, 0),
    NORTHEAST(1, -1),
    SOUTHEAST(1,1),
    NORTHWEST(-1, -1),
    SOUTHWEST(-1, 1);

    private static final Map<Direction2D, Direction2D> INVERSE_DIRECTIONS = Map.of(
            NORTH, SOUTH,
            SOUTH, NORTH,
            EAST, WEST,
            WEST, EAST,
            NORTHEAST, SOUTHWEST,
            SOUTHWEST, NORTHEAST,
            NORTHWEST, SOUTHEAST,
            SOUTHEAST, NORTHWEST
    );

    private int x;
    private int y;

    private Direction2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * returns the inverse direction of the input direction
     * @return
     */
    public static Direction2D getInverseDirection(Direction2D direction) {
        return INVERSE_DIRECTIONS.get(direction);
    }
}
