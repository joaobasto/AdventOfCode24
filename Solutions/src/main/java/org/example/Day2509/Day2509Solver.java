package org.example.Day2509;


import org.example.AbstractSolver;
import org.example.utils.Position2D;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Day2509Solver extends AbstractSolver {

    private static final long NUMBER_OF_CONNECTIONS = 1000;

    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        long result = 0;
        String line;
        Set<Position2D> coordinates = new HashSet<>();
        while ((line = br.readLine()) != null) {
            String[] coordinatesString = line.split(",");
            coordinates.add(new Position2D(Long.parseLong(coordinatesString[0]), Long.parseLong(coordinatesString[1])));
        }
        long maxArea = 0;
        for (Position2D coordinate1 : coordinates) {
            for (Position2D coordinate2 : coordinates) {
                long area = calculateArea(coordinate1, coordinate2);
                if (area > maxArea) {
                    maxArea = area;
                }
            }
        }
        return maxArea;
    }

    private long calculateArea(Position2D coordinate1, Position2D coordinate2) {
        return (Math.abs(coordinate1.getX() - coordinate2.getX()) + 1) *
                (Math.abs(coordinate1.getY() - coordinate2.getY()) + 1);
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        long result = 0;
        System.out.println("Solution created in Python script.")
        return 0;
    }
}
