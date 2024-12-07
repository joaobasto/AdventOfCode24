package org.example.Day6;

import org.example.AbstractSolver;
import org.example.utils.Grid;
import org.example.utils.Position2D;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day6Solver extends AbstractSolver {

    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {

        //initial setup of the grid and the guard
        Grid<PositionData> grid = new Grid<>();
        Guard guard = null;
        String line;
        long y = 0;
        while ((line = br.readLine()) != null) {
            char[] characters = line.toCharArray();
            for(long x = 0; x < characters.length; x++) {
                PositionData positionData;
                if (characters[(int) x] == '#') {
                    positionData = new PositionData(true, false);
                } else if (characters[(int) x] == '.') {
                    positionData = new PositionData(false, false);
                } else {
                    positionData = new PositionData(false, true);
                    guard = new Guard(x, y, characters[(int) x]);
                }
                grid.addElement(x, y, positionData);
            }
            grid.setNumberOfColumns(characters.length);
            y++;
        }
        grid.setNumberOfRows(y);

        //move the guard across the grid
        while (!guard.isOutOfBounds(grid)) {
            guard.move(grid);
        }

        //calculate number of positions visited
        long result = 0;
        for (Map.Entry<Position2D, PositionData> entry : grid.getMap().entrySet()) {
            if (entry.getValue().isBelongsToGaurdPath()) {
                result++;
            }
        }

        return result;
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        //initial setup of the grid and the guard
        Grid<PositionData> grid = new Grid<>();
        Guard guard = null;
        String line;
        long y = 0;
        while ((line = br.readLine()) != null) {
            char[] characters = line.toCharArray();
            for(long x = 0; x < characters.length; x++) {
                PositionData positionData;
                if (characters[(int) x] == '#') {
                    positionData = new PositionData(true, false);
                } else if (characters[(int) x] == '.') {
                    positionData = new PositionData(false, false);
                } else {
                    positionData = new PositionData(false, true);
                    guard = new Guard(x, y, characters[(int) x]);
                }
                grid.addElement(x, y, positionData);
            }
            grid.setNumberOfColumns(characters.length);
            y++;
        }
        grid.setNumberOfRows(y);

        long result = 0;
        for (long x2 = 0; x2 < grid.getNumberOfColumns(); x2++) {
            for (long y2 = 0; y2 < grid.getNumberOfRows(); y2++) {
                //create a copy of the grid
                Grid<PositionData> newGrid = grid.createCopy();
                //add obstacle to grid if not initial position of guard nor existing obstacle
                if (grid.getElement(x2, y2).isObstacle() || grid.getElement(x2, y2).isBelongsToGaurdPath()) {
                    continue;
                }
                newGrid.getElement(x2, y2).setObstacle(true);
                //create a copy of the guard
                Guard newGuard = guard.createCopy();
                //move guard until leaving map or getting in a loop
                while (true) {
                    newGuard.move(newGrid);
                    if (newGuard.isOutOfBounds(newGrid)) {
                        break;
                    } else if (newGuard.isReachedLoop()) {
                        result++;
                        break;
                    }
                }
            }
        }
        return result;
    }
}
