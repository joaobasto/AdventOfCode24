package org.example.Day15;

import org.example.AbstractSolver;
import org.example.Day6.Guard;
import org.example.utils.Direction2D;
import org.example.utils.Grid;
import org.example.utils.Position2D;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.example.Day15.Content.*;

public class Day15Solver extends AbstractSolver {

    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        //initial setup of the grid and the guard
        Grid<Content> grid = new Grid<>();
        Robot robot = null;
        String line;
        long y = 0;
        while (!(line = br.readLine()).isEmpty()) {
            char[] characters = line.toCharArray();
            for(long x = 0; x < characters.length; x++) {
                Content content;
                if (characters[(int) x] == '@') {
                    content = Content.ROBOT;
                    robot = new Robot(x, y);
                } else if (characters[(int) x] == '.') {
                    content = Content.EMPTY;
                } else if (characters[(int) x] == '#') {
                    content = WALL;
                } else {
                    content = Content.BOX;
                }
                grid.addElement(x, y, content);
            }
            grid.setNumberOfColumns(characters.length);
            y++;
        }
        grid.setNumberOfRows(y);

        while ((line = br.readLine()) != null) {
            List<Character> characters = line.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
            for (Character character : characters) {
                robot.setDirection(character);
                Position2D directionVector = new Position2D(robot.getCurrentDirection().getX(), robot.getCurrentDirection().getY());
                Position2D nextSpace = robot.getCurrentPosition().positionAfterMovement(directionVector, 1);
                //case 1: space is empty
                if (grid.getElement(nextSpace) == Content.EMPTY) {
                    grid.setElement(robot.getCurrentPosition(), Content.EMPTY);
                    grid.setElement(nextSpace, Content.ROBOT);
                    robot.setCurrentPosition(nextSpace);
                } else if (grid.getElement(nextSpace) == WALL) {
                    //case 2: space is a wall, do nothing
                } else if (grid.getElement(nextSpace) == Content.BOX) {
                    //case 3: space is a box
                    //find first position that is not a box
                    Position2D firstNonBoxPosition = nextSpace;
                    while (true) {
                        firstNonBoxPosition = firstNonBoxPosition.positionAfterMovement(directionVector, 1);
                        if (grid.getElement(firstNonBoxPosition) != Content.BOX) {
                            break;
                        }
                    }
                    //if that position is empty, we shift everything in that direction
                    if (grid.getElement(firstNonBoxPosition) == Content.EMPTY) {
                        grid.setElement(robot.getCurrentPosition(), Content.EMPTY);
                        grid.setElement(nextSpace, Content.ROBOT);
                        robot.setCurrentPosition(nextSpace);
                        grid.setElement(firstNonBoxPosition, Content.BOX);
                    }
                    //if that position is a wall, we do nothing
                }
            }
        }

        long result = 0;
        for (Position2D position : grid.getMap().keySet()) {
            if (grid.getElement(position) == Content.BOX) {
                result += position.getX() + position.getY() * 100;
            }
        }

        return result;
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        //initial setup of the grid and the guard
        Grid<Content> grid = new Grid<>();
        Robot robot = null;
        String line;
        long y = 0;
        while (!(line = br.readLine()).isEmpty()) {
            char[] characters = line.toCharArray();
            for(long x = 0; x < characters.length; x++) {
                Content content;
                if (characters[(int) x] == '@') {
                    content = Content.ROBOT;
                    robot = new Robot(2*x, y);
                    grid.addElement(2*x, y, content);
                    grid.addElement(2*x + 1, y, Content.EMPTY);
                } else if (characters[(int) x] == '.') {
                    content = Content.EMPTY;
                    grid.addElement(2*x, y, content);
                    grid.addElement(2*x + 1, y, content);
                } else if (characters[(int) x] == '#') {
                    content = WALL;
                    grid.addElement(2*x, y, content);
                    grid.addElement(2*x + 1, y, content);
                } else {
                    grid.addElement(2*x, y, Content.BOX_LEFT_SIDE);
                    grid.addElement(2*x + 1, y, BOX_RIGHT_SIDE);
                }
            }
            grid.setNumberOfColumns(2L*characters.length);
            y++;
        }
        grid.setNumberOfRows(y);

        while ((line = br.readLine()) != null) {
            List<Character> characters = line.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
            for (Character character : characters) {
                robot.setDirection(character);
                Position2D directionVector = new Position2D(robot.getCurrentDirection().getX(), robot.getCurrentDirection().getY());
                Position2D nextSpace = robot.getCurrentPosition().positionAfterMovement(directionVector, 1);
                //case 1: space is empty
                if (grid.getElement(nextSpace) == Content.EMPTY) {
                    grid.setElement(robot.getCurrentPosition(), Content.EMPTY);
                    grid.setElement(nextSpace, Content.ROBOT);
                    robot.setCurrentPosition(nextSpace);
                } else if (grid.getElement(nextSpace) == WALL) {
                    //case 2: space is a wall, do nothing
                } else if (grid.getElement(nextSpace) == Content.BOX_LEFT_SIDE && (
                        robot.getCurrentDirection() == Direction2D.EAST || robot.getCurrentDirection() == Direction2D.WEST
                        ) ) {
                    //case 3: space is a box
                    //find first position that is not a box
                    Position2D firstNonBoxPosition = nextSpace;
                    while (true) {
                        firstNonBoxPosition = firstNonBoxPosition.positionAfterMovement(directionVector, 1);
                        if (grid.getElement(firstNonBoxPosition) != Content.BOX_LEFT_SIDE && grid.getElement(firstNonBoxPosition) != BOX_RIGHT_SIDE) {
                            break;
                        }
                    }
                    //if that position is empty, we shift everything in that direction
                    if (grid.getElement(firstNonBoxPosition) == Content.EMPTY) {
                        grid.setElement(robot.getCurrentPosition(), Content.EMPTY);
                        grid.setElement(nextSpace, Content.ROBOT);
                        robot.setCurrentPosition(nextSpace);
                        grid.setElement(firstNonBoxPosition, BOX_RIGHT_SIDE);
                        for (long i = nextSpace.getX() + 1; i < firstNonBoxPosition.getX(); i++) {
                            if (grid.getElement(i, nextSpace.getY()) == Content.BOX_LEFT_SIDE) {
                                grid.setElement(new Position2D(i, nextSpace.getY()), BOX_RIGHT_SIDE);
                            } else {
                                grid.setElement(new Position2D(i, nextSpace.getY()), Content.BOX_LEFT_SIDE);
                            }
                        }
                    }
                    //if that position is a wall, we do nothing
                } else if (grid.getElement(nextSpace) == BOX_RIGHT_SIDE && (
                        robot.getCurrentDirection() == Direction2D.EAST || robot.getCurrentDirection() == Direction2D.WEST
                ) ) {
                    //case 3: space is a box
                    //find first position that is not a box
                    Position2D firstNonBoxPosition = nextSpace;
                    while (true) {
                        firstNonBoxPosition = firstNonBoxPosition.positionAfterMovement(directionVector, 1);
                        if (grid.getElement(firstNonBoxPosition) != Content.BOX_LEFT_SIDE && grid.getElement(firstNonBoxPosition) != BOX_RIGHT_SIDE) {
                            break;
                        }
                    }
                    //if that position is empty, we shift everything in that direction
                    if (grid.getElement(firstNonBoxPosition) == Content.EMPTY) {
                        grid.setElement(robot.getCurrentPosition(), Content.EMPTY);
                        grid.setElement(nextSpace, Content.ROBOT);
                        robot.setCurrentPosition(nextSpace);
                        grid.setElement(firstNonBoxPosition, Content.BOX_LEFT_SIDE);
                        for (long i = nextSpace.getX() - 1; i > firstNonBoxPosition.getX(); i--) {
                            if (grid.getElement(i, nextSpace.getY()) == Content.BOX_LEFT_SIDE) {
                                grid.setElement(new Position2D(i, nextSpace.getY()), BOX_RIGHT_SIDE);
                            } else {
                                grid.setElement(new Position2D(i, nextSpace.getY()), Content.BOX_LEFT_SIDE);
                            }
                        }
                    }
                    //if that position is a wall, we do nothing
                } else {
                    //get range of positions in next row
                    long yAux = nextSpace.getY();
                    long minX = nextSpace.getX();
                    if (grid.getElement(minX, yAux) == BOX_RIGHT_SIDE) {
                        minX = minX - 1;
                    }
                    //define a map of set of boxesToExplore per row, with the one in the next space
                    Set<Position2D> boxesToExplorePositionsSet = new HashSet<>();
                    boxesToExplorePositionsSet.add(new Position2D(minX, yAux));
                    //define a map of set of exploredBoxes per row
                    NavigableMap<Long, Set<Position2D>> exploredBoxesPositions = new TreeMap<>();
                    boolean isMovable = true;
                    //while there are boxes to explore
                    while (!boxesToExplorePositionsSet.isEmpty()) {
                        //get next box position
                        Position2D boxPosition = boxesToExplorePositionsSet.iterator().next();
                        //check if there is a wall on the left and right position above
                        //if yes, set is movable to false and break;
                        Position2D nextPositionLeft = boxPosition.positionAfterMovement(directionVector, 1);
                        if (grid.getElement(nextPositionLeft.getX(), nextPositionLeft.getY()) == WALL ||
                                grid.getElement(nextPositionLeft.getX() + 1, nextPositionLeft.getY()) == WALL) {
                            isMovable = false;
                            break;
                        }
                        //check if on the left side there is a right or left side of the box
                        //if yes, include it on the boxes to explore
                        if (grid.getElement(nextPositionLeft.getX(), nextPositionLeft.getY()) == BOX_LEFT_SIDE) {
                            boxesToExplorePositionsSet.add(nextPositionLeft);
                        } else if (grid.getElement(nextPositionLeft.getX(), nextPositionLeft.getY()) == BOX_RIGHT_SIDE) {
                            boxesToExplorePositionsSet.add(new Position2D(nextPositionLeft.getX() - 1, nextPositionLeft.getY()));
                        }
                        //check if on the right side there is a left side of the box
                        //if yes, include it on the boxes to explore
                        if (grid.getElement(nextPositionLeft.getX() + 1, nextPositionLeft.getY()) == BOX_LEFT_SIDE) {
                            boxesToExplorePositionsSet.add(new Position2D(nextPositionLeft.getX() + 1, nextPositionLeft.getY()));
                        }
                        //remove this box from the boxes to explore and add it to the explored boxes
                        boxesToExplorePositionsSet.remove(boxPosition);
                        exploredBoxesPositions.computeIfAbsent(boxPosition.getY(), k -> new HashSet<>()).add(boxPosition);
                    }

                    if (isMovable) {
                        //if direction is NORTH, get smallest row; if is SOUTH, get bigger row
                        if (directionVector.getY() == 1 ) {
                            exploredBoxesPositions = exploredBoxesPositions.descendingMap();
                        }
                        //while map is not empty
                        while (!exploredBoxesPositions.isEmpty()) {
                            //get next row
                            Set<Position2D> boxPositions = exploredBoxesPositions.firstEntry().getValue();
                            //apply movement to all boxes
                            Iterator<Position2D> iterator = boxPositions.iterator();
                            while (iterator.hasNext()) {
                                Position2D boxPosition = iterator.next();
                                Position2D newBoxPosition = boxPosition.positionAfterMovement(directionVector, 1);
                                grid.setElement(boxPosition, EMPTY);
                                grid.setElement(newBoxPosition, BOX_LEFT_SIDE);
                                grid.setElement(new Position2D(boxPosition.getX() + 1, boxPosition.getY()), EMPTY);
                                grid.setElement(new Position2D(newBoxPosition.getX() + 1, newBoxPosition.getY()), BOX_RIGHT_SIDE);
                            }
                            //remove from map
                            exploredBoxesPositions.remove(exploredBoxesPositions.firstEntry().getKey());
                        }
                        //move robot
                        grid.setElement(robot.getCurrentPosition(), Content.EMPTY);
                        grid.setElement(nextSpace, Content.ROBOT);
                        robot.setCurrentPosition(nextSpace);
                    }
                }
            }
        }

        long result = 0;
        for (Position2D position : grid.getMap().keySet()) {
            if (grid.getElement(position) == Content.BOX_LEFT_SIDE) {
                result += position.getX() + position.getY() * 100;
            }
        }

        return result;
    }
}
