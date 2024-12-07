package org.example.Day6;

import org.example.utils.Direction2D;
import org.example.utils.Grid;
import org.example.utils.Position2D;

import static org.example.utils.Direction2D.*;

public class Guard {

    private Position2D currentPosition;
    private Direction2D currentDirection;

    public Guard(long x, long y, char directionChar) {
        this.currentPosition = new Position2D(x, y);
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

    public boolean isOutOfBounds(Grid<PositionData> grid) {
        return !grid.isPositionInGrid(this.currentPosition.getX(), this.currentPosition.getY());
    }

    public void move(Grid<PositionData> grid) {
        long x = this.currentPosition.getX() + this.currentDirection.getX();
        long y = this.currentPosition.getY() + this.currentDirection.getY();
        if (grid.getElement(x, y) == null) {
            this.currentPosition = new Position2D(x, y);
        } else if (grid.getElement(x, y).isObstacle()) {
            Direction2D newDirection = null;
            switch (currentDirection) {
                case NORTH:
                    newDirection = EAST;
                    break;
                case EAST:
                    newDirection = SOUTH;
                    break;
                case SOUTH:
                    newDirection = WEST;
                    break;
                case WEST:
                    newDirection = NORTH;
                    break;
            }
            this.currentDirection = newDirection;
        } else {
            this.currentPosition = new Position2D(x, y);
            grid.getElement(x, y).setBelongsToGaurdPath(true);
        }
    }
}
