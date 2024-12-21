package org.example.Day15;

import org.example.AbstractSolver;
import org.example.Day6.Guard;
import org.example.utils.Grid;
import org.example.utils.Position2D;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
                    content = Content.WALL;
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
                } else if (grid.getElement(nextSpace) == Content.WALL) {
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
        return 0;
    }
}
