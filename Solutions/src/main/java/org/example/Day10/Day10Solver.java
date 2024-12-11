package org.example.Day10;

import org.example.AbstractSolver;
import org.example.utils.Grid;
import org.example.utils.Position2D;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Day10Solver extends AbstractSolver {
    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        //initial setup of the grid and the guard
        Grid<Integer> grid = new Grid<>();
        List<Position2D> trailheadPositions = new ArrayList<>();
        String line;
        long y = 0;
        while ((line = br.readLine()) != null) {
            char[] characters = line.toCharArray();
            for(long x = 0; x < characters.length; x++) {
                Integer height = Character.getNumericValue(characters[(int) x]);
                if (height == 0) {
                    trailheadPositions.add(new Position2D(x, y));
                }
                grid.addElement(x, y, height);
            }
            grid.setNumberOfColumns(characters.length);
            y++;
        }
        grid.setNumberOfRows(y);

        long result = 0;
        for (Position2D trailheadPosition : trailheadPositions) {
            Set<Position2D> reachableFinishes = new HashSet<>();
            calculateReachableFinishes(trailheadPosition, grid, reachableFinishes);
            result += reachableFinishes.size();
        }
        return result;
    }

    private void calculateReachableFinishes(Position2D currentPosition, Grid<Integer> grid,
                                                       Set<Position2D> reachableFinishes) {
        //check for terminal conditions
        if (grid.getElement(currentPosition).equals(9)) {
            reachableFinishes.add(currentPosition);
            return;
        }
        List<Position2D> validNeighbors = getValidNeighbors(currentPosition, grid);
        if (validNeighbors.isEmpty()) {
            return;
        }

        for(Position2D position : validNeighbors) {
            calculateReachableFinishes(position, grid, reachableFinishes);
        }
    }

    private List<Position2D> getValidNeighbors(Position2D trailheadPosition, Grid<Integer> grid) {
        long x = trailheadPosition.getX();
        long y = trailheadPosition.getY();
        Integer height = grid.getElement(trailheadPosition);

        List<Position2D> validNeighbors = new ArrayList<>();
        if (grid.isPositionInGrid(x - 1, y)) {
            if (grid.getElement(x - 1, y).equals(height + 1)) {
                validNeighbors.add(new Position2D(x - 1, y));
            }
        }
        if (grid.isPositionInGrid(x + 1, y)) {
            if (grid.getElement(x + 1, y).equals(height + 1)) {
                validNeighbors.add(new Position2D(x + 1, y));
            }
        }
        if (grid.isPositionInGrid(x, y - 1)) {
            if (grid.getElement(x, y - 1).equals(height + 1)) {
                validNeighbors.add(new Position2D(x, y - 1));
            }
        }
        if (grid.isPositionInGrid(x, y + 1)) {
            if (grid.getElement(x, y + 1).equals(height + 1)) {
                validNeighbors.add(new Position2D(x, y + 1));
            }
        }
        return validNeighbors;
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        //initial setup of the grid and the guard
        Grid<Integer> grid = new Grid<>();
        List<Position2D> trailheadPositions = new ArrayList<>();
        String line;
        long y = 0;
        while ((line = br.readLine()) != null) {
            char[] characters = line.toCharArray();
            for(long x = 0; x < characters.length; x++) {
                Integer height = Character.getNumericValue(characters[(int) x]);
                if (height == 0) {
                    trailheadPositions.add(new Position2D(x, y));
                }
                grid.addElement(x, y, height);
            }
            grid.setNumberOfColumns(characters.length);
            y++;
        }
        grid.setNumberOfRows(y);

        long result = 0;
        for (Position2D trailheadPosition : trailheadPositions) {
            List<Position2D> reachableFinishes = new ArrayList<>();
            calculateReachableFinishes(trailheadPosition, grid, reachableFinishes);
            result += reachableFinishes.size();
        }
        return result;
    }

    private void calculateReachableFinishes(Position2D currentPosition, Grid<Integer> grid,
                                            List<Position2D> reachableFinishes) {
        //check for terminal conditions
        if (grid.getElement(currentPosition).equals(9)) {
            reachableFinishes.add(currentPosition);
            return;
        }
        List<Position2D> validNeighbors = getValidNeighbors(currentPosition, grid);
        if (validNeighbors.isEmpty()) {
            return;
        }

        for(Position2D position : validNeighbors) {
            calculateReachableFinishes(position, grid, reachableFinishes);
        }
    }
}
