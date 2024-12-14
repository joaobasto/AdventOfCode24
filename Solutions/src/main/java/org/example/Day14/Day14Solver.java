package org.example.Day14;

import org.example.AbstractSolver;
import org.example.utils.Position2D;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14Solver extends AbstractSolver {
    private static final long NUMBER_OF_COLUMNS = 101;
    private static final long NUMBER_OF_ROWS = 103;
    private static final long NUMBER_OF_SECONDS = 100;

    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        String line;
        long result = 0;
        Map<Long, Long> countByPosition = new HashMap<>();
        while ((line = br.readLine()) != null) {
            Pattern pattern = Pattern.compile("p=([-\\d]+),([-\\d]+) v=([-\\d]+),([-\\d]+)");
            Matcher matcher = pattern.matcher(line);
            matcher.find();
            long pX = Long.parseLong(matcher.group(1));
            long pY = Long.parseLong(matcher.group(2));
            long vX = Long.parseLong(matcher.group(3));
            vX = vX >= 0 ? vX : NUMBER_OF_COLUMNS + vX;
            long vY = Long.parseLong(matcher.group(4));
            vY = vY >= 0 ? vY : NUMBER_OF_ROWS + vY;

            pX = pX + NUMBER_OF_SECONDS * vX;
            pX = pX % NUMBER_OF_COLUMNS;
            pY = pY + NUMBER_OF_SECONDS * vY;
            pY = pY % NUMBER_OF_ROWS;

            Long quadrant = 0L;
            //determining the quadrant (1 - NW, 2 - NE, 3 - SE, 4 - SW)
            if (pX < NUMBER_OF_COLUMNS/2L && pY < NUMBER_OF_ROWS/2L) {
                quadrant = 1L;
            } else if (pX > NUMBER_OF_COLUMNS/2L && pY < NUMBER_OF_ROWS/2L) {
                quadrant = 2L;
            } else if (pX > NUMBER_OF_COLUMNS/2L && pY > NUMBER_OF_ROWS/2L) {
                quadrant = 3L;
            } else if (pX < NUMBER_OF_COLUMNS/2L && pY > NUMBER_OF_ROWS/2L) {
                quadrant = 4L;
            }
            if (quadrant != 0L) {
                countByPosition.merge(quadrant, 1L, Long::sum);
            }
        }
        result = 1L;
        for (Long value : countByPosition.values()) {
            result = result * value;
        }
        return result;
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        String line;
        long result = 0;

        char [][] grid = new char[(int) NUMBER_OF_ROWS][(int) NUMBER_OF_COLUMNS];
        boolean [][] booleanGrid = new boolean[(int) NUMBER_OF_ROWS][(int) NUMBER_OF_COLUMNS];
        boolean [][] firstMask = {
                {true, true, true, false, true, true, true},
                {true, true, false, false, false, true, true},
                {true, false, false, false, false, false, true},
                {false, false, false, false, false, false, false},
        };

        List<Position2D> initialPositions = new ArrayList<>();
        List<Position2D> velocity = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            Pattern pattern = Pattern.compile("p=([-\\d]+),([-\\d]+) v=([-\\d]+),([-\\d]+)");
            Matcher matcher = pattern.matcher(line);
            matcher.find();
            long pX = Long.parseLong(matcher.group(1));
            long pY = Long.parseLong(matcher.group(2));
            initialPositions.add(new Position2D(pX, pY));
            long vX = Long.parseLong(matcher.group(3));
            vX = vX >= 0 ? vX : NUMBER_OF_COLUMNS + vX;
            long vY = Long.parseLong(matcher.group(4));
            vY = vY >= 0 ? vY : NUMBER_OF_ROWS + vY;
            velocity.add(new Position2D(vX, vY));
        }

        long n_seconds = 0;
        while (true) {
            //clear graph
            for (int i = 0; i < NUMBER_OF_ROWS; i++) {
                for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
                    grid[i][j] = '.';
                    booleanGrid[i][j] = false;
                }
            }
            //write positions of robots
            for (int i = 0; i < initialPositions.size(); i++) {
                long pX, pY;
                pX = initialPositions.get(i).getX() + n_seconds * velocity.get(i).getX();
                pX = pX % NUMBER_OF_COLUMNS;
                pY = initialPositions.get(i).getY() + n_seconds * velocity.get(i).getY();
                pY = pY % NUMBER_OF_ROWS;
                grid[(int) pY][(int) pX] = '*';
                booleanGrid[(int) pY][(int) pX] = true;
            }

            //check if there is a match with the first mask
            boolean isMatch = true;
            for (int i = 0; i < NUMBER_OF_ROWS - 3; i++) {
                for (int j = 0; j < NUMBER_OF_COLUMNS - 6; j++) {
                    isMatch = true;
                    for (int k = 0; k < 4; k++) {
                        for (int l = 0; l < 7; l++) {
                            isMatch = firstMask[k][l] || booleanGrid[i + k][j + l];
                            if (!isMatch) {
                                break;
                            }
                        }
                        if (!isMatch) {
                            break;
                        }
                    }
                    if (isMatch) {
                        break;
                    }
                }
                if (isMatch) {
                    break;
                }
            }

            //print graph
            if (isMatch) {
                printGraph(n_seconds, grid);
                return 0;
            }
            n_seconds++;
        }
    }

    private static void printGraph(long n_seconds, char[][] grid) {
        System.out.println("Seconds: " + n_seconds);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
    }
}
