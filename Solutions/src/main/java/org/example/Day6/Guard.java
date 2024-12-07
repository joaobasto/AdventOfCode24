package org.example.Day6;

import org.example.utils.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.example.utils.Direction2D.*;

public class Guard implements Copiable<Guard> {

    private Position2D currentPosition;
    private Direction2D currentDirection;
    private Set<TrajectoryPoint2D> previousTrajectoryPoints = new HashSet<>();
    private boolean reachedLoop = false;

    public Position2D getCurrentPosition() {
        return currentPosition;
    }

    public boolean isReachedLoop() {
        return reachedLoop;
    }

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

    public Guard(Position2D currentPosition, Direction2D currentDirection) {
        this.currentPosition = currentPosition;
        this.currentDirection = currentDirection;
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
            if (this.previousTrajectoryPoints.contains(new TrajectoryPoint2D(currentPosition, currentDirection))) {
                reachedLoop = true;
            }
            this.previousTrajectoryPoints.add(new TrajectoryPoint2D(currentPosition, currentDirection));
        } else {
            this.currentPosition = new Position2D(x, y);
            grid.getElement(x, y).setBelongsToGaurdPath(true);
            if (this.previousTrajectoryPoints.contains(new TrajectoryPoint2D(currentPosition, currentDirection))) {
                reachedLoop = true;
            }
            this.previousTrajectoryPoints.add(new TrajectoryPoint2D(currentPosition, currentDirection));
        }
    }

    @Override
    public Guard createCopy() {
        return new Guard(this.currentPosition.createCopy(), this.currentDirection);
    }

    public boolean isLoopDetected() {
        return this.previousTrajectoryPoints.contains(new TrajectoryPoint2D(currentPosition, currentDirection));
    }
}
