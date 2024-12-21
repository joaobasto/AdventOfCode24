package org.example.Day15;

import org.example.utils.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.example.utils.Direction2D.*;

public class Robot {

    private Position2D currentPosition;
    private Direction2D currentDirection;

    public Position2D getCurrentPosition() {
        return currentPosition;
    }

    public Robot(long x, long y) {
        this.currentPosition = new Position2D(x, y);
    }

    public Direction2D getCurrentDirection() {
        return currentDirection;
    }

    public void setDirection(char directionChar) {
        switch (directionChar) {
            case '^':
                this.currentDirection = Direction2D.NORTH;
                break;
            case '>':
                this.currentDirection = EAST;
                break;
            case '<':
                this.currentDirection = Direction2D.WEST;
                break;
            case 'v':
                this.currentDirection = Direction2D.SOUTH;
                break;
        }
    }

    public void setCurrentPosition(Position2D currentPosition) {
        this.currentPosition = currentPosition;
    }
}
