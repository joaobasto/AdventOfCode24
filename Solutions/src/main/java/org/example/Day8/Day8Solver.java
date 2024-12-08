package org.example.Day8;

import org.example.AbstractSolver;
import org.example.utils.Grid;
import org.example.utils.MathUtils;
import org.example.utils.Position2D;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Day8Solver extends AbstractSolver {


    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        //initial setup of the grid and the guard
        Grid<Character> grid = new Grid<>();
        Map<Character, List<Position2D>> positionsByCharacter = new HashMap<>();
        String line;
        long y = 0;
        while ((line = br.readLine()) != null) {
            char[] characters = line.toCharArray();
            for(long x = 0; x < characters.length; x++) {
                Character character = characters[(int) x];
                if (character != '.') {
                    positionsByCharacter.computeIfAbsent(character, unused -> new ArrayList<>()).add(new Position2D(x, y));
                }
                grid.addElement(x, y, character);
            }
            grid.setNumberOfColumns(characters.length);
            y++;
        }
        grid.setNumberOfRows(y);

        Set<Position2D> antinodes = new HashSet<>();
        for (List<Position2D> antennaPositions : positionsByCharacter.values()) {
            for (int i = 0; i < antennaPositions.size(); i++) {
                for (int j = i + 1; j < antennaPositions.size(); j++) {
                    //get direction vector
                    Position2D directionVector = new Position2D(antennaPositions.get(j).getX() - antennaPositions.get(i).getX(),
                            antennaPositions.get(j).getY() - antennaPositions.get(i).getY());
                    //get antinode 1: antenna i, moved by the direction with a step size of -1
                    Position2D antinode1 = antennaPositions.get(i).positionAfterMovement(directionVector, -1);
                    //get antinode 2: antenna i, moved by the direction with a step size of +1
                    Position2D antinode2 = antennaPositions.get(j).positionAfterMovement(directionVector, 1);
                    if (grid.isPositionInGrid(antinode1.getX(), antinode1.getY())) {
                        antinodes.add(antinode1);
                    }
                    if (grid.isPositionInGrid(antinode2.getX(), antinode2.getY())) {
                        antinodes.add(antinode2);
                    }
                }
            }
        }

        return antinodes.size();
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        //initial setup of the grid and the guard
        Grid<Character> grid = new Grid<>();
        Map<Character, List<Position2D>> positionsByCharacter = new HashMap<>();
        String line;
        long y = 0;
        while ((line = br.readLine()) != null) {
            char[] characters = line.toCharArray();
            for(long x = 0; x < characters.length; x++) {
                Character character = characters[(int) x];
                if (character != '.') {
                    positionsByCharacter.computeIfAbsent(character, unused -> new ArrayList<>()).add(new Position2D(x, y));
                }
                grid.addElement(x, y, character);
            }
            grid.setNumberOfColumns(characters.length);
            y++;
        }
        grid.setNumberOfRows(y);

        Set<Position2D> antinodes = new HashSet<>();
        for (List<Position2D> antennaPositions : positionsByCharacter.values()) {
            for (int i = 0; i < antennaPositions.size(); i++) {
                for (int j = i + 1; j < antennaPositions.size(); j++) {
                    //get direction vector
                    Position2D directionVector = new Position2D(antennaPositions.get(j).getX() - antennaPositions.get(i).getX(),
                            antennaPositions.get(j).getY() - antennaPositions.get(i).getY());
                    //assure that direction vector is the smallest possible with integer X and Y
                    //this means we calculate the gcd between X and Y and then reduce them by dividing by gcd
                    long gcd = MathUtils.gcd(directionVector.getX(), directionVector.getY());
                    directionVector = new Position2D(directionVector.getX()/gcd, directionVector.getY()/gcd);
                    //search for nodes starting on antenna i, and with a positive step, until out of bounds
                    long stepSize = 0;
                    while (true) {
                        Position2D antinode = antennaPositions.get(i).positionAfterMovement(directionVector, stepSize);
                        if (grid.isPositionInGrid(antinode.getX(), antinode.getY())) {
                            antinodes.add(antinode);
                        } else {
                            break;
                        }
                        stepSize++;
                    }
                    //search for nodes starting on antenna i, and with a negative step, until out of bounds
                    stepSize = -1;
                    while (true) {
                        Position2D antinode = antennaPositions.get(i).positionAfterMovement(directionVector, stepSize);
                        if (grid.isPositionInGrid(antinode.getX(), antinode.getY())) {
                            antinodes.add(antinode);
                        } else {
                            break;
                        }
                        stepSize--;
                    }
                }
            }
        }

        return antinodes.size();
    }
}
