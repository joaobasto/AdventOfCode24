package org.example.Day2507;

import org.example.AbstractSolver;
import org.example.utils.Grid;
import org.example.utils.Position2D;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day2507Solver extends AbstractSolver {

    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {

        //initial setup of the grid and the guard
        Grid<Boolean> grid = new Grid<>();
        long startPositionX = 0;
        Map<Long, List<Long>> splitterPositionsMap = new HashMap<>();
        String line;
        long y = 0;
        while ((line = br.readLine()) != null) {
            char[] characters = line.toCharArray();
            for(long x = 0; x < characters.length; x++) {
                if (characters[(int) x] == 'S') {
                    startPositionX = x;
                } else if (characters[(int) x] == '^') {
                    splitterPositionsMap.put(y, splitterPositionsMap.getOrDefault(y, new ArrayList<Long>()));
                    splitterPositionsMap.get(y).add(x);
                }
            }
            grid.setNumberOfColumns(characters.length);
            y++;
        }
        grid.setNumberOfRows(y);

        long result = 0;
        Set<Long> currentPositions = new HashSet<>();
        currentPositions.add(startPositionX);
        for(long i = 1; i < grid.getNumberOfRows(); i++) {
            //create future positions set
            Set<Long> nextCurrentPositions = new HashSet<>();
            //process each current position's transformation
            for(Long position : currentPositions) {
                if (splitterPositionsMap.getOrDefault(i, new ArrayList<>()).contains(position)) {
                    nextCurrentPositions.add(position - 1);
                    nextCurrentPositions.add(position + 1);
                    result++;
                } else {
                    nextCurrentPositions.add(position);
                }
            }
            currentPositions.clear();
            currentPositions.addAll(nextCurrentPositions);
        }

        return result;
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        //initial setup of the grid and the guard
        Grid<Boolean> grid = new Grid<>();
        long startPositionX = 0;
        Map<Long, List<Long>> splitterPositionsMap = new HashMap<>();
        String line;
        long y = 0;
        while ((line = br.readLine()) != null) {
            char[] characters = line.toCharArray();
            for(long x = 0; x < characters.length; x++) {
                if (characters[(int) x] == 'S') {
                    startPositionX = x;
                } else if (characters[(int) x] == '^') {
                    splitterPositionsMap.put(y, splitterPositionsMap.getOrDefault(y, new ArrayList<Long>()));
                    splitterPositionsMap.get(y).add(x);
                }
            }
            grid.setNumberOfColumns(characters.length);
            y++;
        }
        grid.setNumberOfRows(y);

        Map<Long, Long> currentPositionsCount = new HashMap<>();
        currentPositionsCount.put(startPositionX, 1L);
        for(long i = 1; i < grid.getNumberOfRows(); i++) {
            //create future positions set
            Map<Long, Long> nextCurrentPositionsCount = new HashMap<>();
            //process each current position's transformation
            for(Long position : currentPositionsCount.keySet()) {
                if (splitterPositionsMap.getOrDefault(i, new ArrayList<>()).contains(position)) {
                    long count1 = nextCurrentPositionsCount.getOrDefault(position - 1, 0L);
                    nextCurrentPositionsCount.put(position - 1, count1 + currentPositionsCount.get(position));
                    long count2 = nextCurrentPositionsCount.getOrDefault(position + 1, 0L);
                    nextCurrentPositionsCount.put(position + 1, count2 + currentPositionsCount.get(position));
                } else {
                    long count1 = nextCurrentPositionsCount.getOrDefault(position, 0L);
                    nextCurrentPositionsCount.put(position, count1 + currentPositionsCount.get(position));
                }
            }
            currentPositionsCount.clear();
            currentPositionsCount.putAll(nextCurrentPositionsCount);
        }

        return currentPositionsCount.values().stream().mapToLong(Long::longValue).sum();
    }
}
