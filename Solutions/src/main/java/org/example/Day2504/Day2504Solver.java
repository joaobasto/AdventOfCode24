package org.example.Day2504;

import org.example.AbstractSolver;
import org.example.utils.Grid;
import org.example.utils.Position2D;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Day2504Solver extends AbstractSolver {

    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        //initial setup of the grid and the guard
        Grid<Boolean> grid = new Grid<>();
        String line;
        long y = 0;
        while ((line = br.readLine()) != null) {
            char[] characters = line.toCharArray();
            for(long x = 0; x < characters.length; x++) {
                Boolean hasRoll = characters[(int) x] == '@';
                grid.addElement(x, y, hasRoll);
            }
            grid.setNumberOfColumns(characters.length);
            y++;
        }
        grid.setNumberOfRows(y);

        long result = calculateNumberOfReachablePositions(grid);
        return result;
    }

    private long calculateNumberOfReachablePositions(Grid<Boolean> grid) {
        return grid.getMap().entrySet().stream()
                .filter(entry -> isEntryValid(entry, grid)).count();
    }

    private boolean isEntryValid(Map.Entry<Position2D, Boolean> entry, Grid<Boolean> grid) {
        if (entry.getValue() == false) {
            return false;
        }

        int countAdjacentRolls = 0;

        if (grid.isPositionInGrid(entry.getKey().getX() - 1, entry.getKey().getY() - 1)) {
            if (grid.getElement(entry.getKey().getX() - 1, entry.getKey().getY() - 1) == true) {
                countAdjacentRolls++;
            }
        }
        if (grid.isPositionInGrid(entry.getKey().getX() - 1, entry.getKey().getY())) {
            if (grid.getElement(entry.getKey().getX() - 1, entry.getKey().getY()) == true) {
                countAdjacentRolls++;
            }
        }
        if (grid.isPositionInGrid(entry.getKey().getX() - 1, entry.getKey().getY() + 1)) {
            if (grid.getElement(entry.getKey().getX() - 1, entry.getKey().getY() + 1) == true) {
                countAdjacentRolls++;
            }
        }
        if (grid.isPositionInGrid(entry.getKey().getX(), entry.getKey().getY() - 1)) {
            if (grid.getElement(entry.getKey().getX(), entry.getKey().getY() - 1) == true) {
                countAdjacentRolls++;
            }
        }
        if (grid.isPositionInGrid(entry.getKey().getX(), entry.getKey().getY() + 1)) {
            if (grid.getElement(entry.getKey().getX(), entry.getKey().getY() + 1) == true) {
                countAdjacentRolls++;
            }
        }
        if (grid.isPositionInGrid(entry.getKey().getX() + 1, entry.getKey().getY() - 1)) {
            if (grid.getElement(entry.getKey().getX() + 1, entry.getKey().getY() - 1) == true) {
                countAdjacentRolls++;
            }
        }
        if (grid.isPositionInGrid(entry.getKey().getX() + 1, entry.getKey().getY())) {
            if (grid.getElement(entry.getKey().getX() + 1, entry.getKey().getY()) == true) {
                countAdjacentRolls++;
            }
        }
        if (grid.isPositionInGrid(entry.getKey().getX() + 1, entry.getKey().getY() + 1)) {
            if (grid.getElement(entry.getKey().getX() + 1, entry.getKey().getY() + 1) == true) {
                countAdjacentRolls++;
            }
        }

        return countAdjacentRolls < 4;
    }



    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        //initial setup of the grid and the guard
        Grid<Boolean> grid = new Grid<>();
        String line;
        long y = 0;
        while ((line = br.readLine()) != null) {
            char[] characters = line.toCharArray();
            for(long x = 0; x < characters.length; x++) {
                Boolean hasRoll = characters[(int) x] == '@';
                grid.addElement(x, y, hasRoll);
            }
            grid.setNumberOfColumns(characters.length);
            y++;
        }
        grid.setNumberOfRows(y);

        long result = 0;

        while (true) {
            long oldResult = result;
            result = result + removeAllReachableRolls(grid);
            if (oldResult == result) {
                break;
            }
        }
        return result;
    }

    private long removeAllReachableRolls(Grid<Boolean> grid) {
        return grid.getMap().entrySet().stream()
                .filter(entry -> isEntryValid(entry, grid))
                .peek(entry -> entry.setValue(false)).count();
    }
}
