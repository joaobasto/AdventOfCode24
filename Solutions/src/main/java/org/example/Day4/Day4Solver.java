package org.example.Day4;

import org.example.AbstractSolver;
import org.example.utils.Direction2D;
import org.example.utils.Position2D;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Day4Solver extends AbstractSolver {


    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        Map<Position2D, Character> map = new HashMap<>();
        String line;
        long y = 0;
        while ((line = br.readLine()) != null) {
            char[] characters = line.toCharArray();
            for(long x = 0; x < characters.length; x++) {
                map.put(new Position2D(x, y), characters[(int) x]);
            }
            y++;
        }

        long result = 0;
        for (Position2D position: map.keySet()) {
            for (Direction2D direction : Direction2D.values()) {
                if (isPositionValidForDirection(map, position, direction)) {
                    result = checkWord(map, position, direction) ? result + 1: result;
                }
            }
        }

        return result;
    }

    private boolean checkWord(Map<Position2D, Character> map, Position2D position, Direction2D direction) {
        switch (direction) {
            case NORTH:
                return map.get(position).equals('X')
                        && map.get(new Position2D(position.getX(), position.getY()-1)).equals('M')
                        && map.get(new Position2D(position.getX(), position.getY()-2)).equals('A')
                        && map.get(new Position2D(position.getX(), position.getY()-3)).equals('S');
            case SOUTH:
                return map.get(position).equals('X')
                        && map.get(new Position2D(position.getX(), position.getY()+1)).equals('M')
                        && map.get(new Position2D(position.getX(), position.getY()+2)).equals('A')
                        && map.get(new Position2D(position.getX(), position.getY()+3)).equals('S');
            case EAST:
                return map.get(position).equals('X')
                        && map.get(new Position2D(position.getX()+1, position.getY())).equals('M')
                        && map.get(new Position2D(position.getX()+2, position.getY())).equals('A')
                        && map.get(new Position2D(position.getX()+3, position.getY())).equals('S');
            case WEST:
                return map.get(position).equals('X')
                        && map.get(new Position2D(position.getX()-1, position.getY())).equals('M')
                        && map.get(new Position2D(position.getX()-2, position.getY())).equals('A')
                        && map.get(new Position2D(position.getX()-3, position.getY())).equals('S');
            case NORTHEAST:
                return map.get(position).equals('X')
                        && map.get(new Position2D(position.getX()+1, position.getY()-1)).equals('M')
                        && map.get(new Position2D(position.getX()+2, position.getY()-2)).equals('A')
                        && map.get(new Position2D(position.getX()+3, position.getY()-3)).equals('S');
            case NORTHWEST:
                return map.get(position).equals('X')
                        && map.get(new Position2D(position.getX()-1, position.getY()-1)).equals('M')
                        && map.get(new Position2D(position.getX()-2, position.getY()-2)).equals('A')
                        && map.get(new Position2D(position.getX()-3, position.getY()-3)).equals('S');
            case SOUTHEAST:
                return map.get(position).equals('X')
                        && map.get(new Position2D(position.getX()+1, position.getY()+1)).equals('M')
                        && map.get(new Position2D(position.getX()+2, position.getY()+2)).equals('A')
                        && map.get(new Position2D(position.getX()+3, position.getY()+3)).equals('S');
            case SOUTHWEST:
                return map.get(position).equals('X')
                        && map.get(new Position2D(position.getX()-1, position.getY()+1)).equals('M')
                        && map.get(new Position2D(position.getX()-2, position.getY()+2)).equals('A')
                        && map.get(new Position2D(position.getX()-3, position.getY()+3)).equals('S');
        }
        return false;
    }

    private boolean checkWord2(Map<Position2D, Character> map, Position2D position) {
        return map.get(position).equals('A') &&
                ( (map.get(new Position2D(position.getX()-1, position.getY()-1)).equals('M')  &&
                        map.get(new Position2D(position.getX()+1, position.getY()+1)).equals('S') ) ||
                        (map.get(new Position2D(position.getX()-1, position.getY()-1)).equals('S')  &&
                                map.get(new Position2D(position.getX()+1, position.getY()+1)).equals('M') )
                ) &&
                ( (map.get(new Position2D(position.getX()-1, position.getY()+1)).equals('M')  &&
                        map.get(new Position2D(position.getX()+1, position.getY()-1)).equals('S') ) ||
                        (map.get(new Position2D(position.getX()-1, position.getY()+1)).equals('S')  &&
                                map.get(new Position2D(position.getX()+1, position.getY()-1)).equals('M') )
                );
    }

    private boolean isPositionValidForDirection(Map<Position2D, Character> map, Position2D position, Direction2D direction) {
        switch (direction) {
            case NORTH:
                return map.containsKey(new Position2D(position.getX(), position.getY() - 3));
            case SOUTH:
                return map.containsKey(new Position2D(position.getX(), position.getY() + 3));
            case EAST:
                return map.containsKey(new Position2D(position.getX() + 3, position.getY()));
            case WEST:
                return map.containsKey(new Position2D(position.getX() - 3, position.getY()));
            case NORTHEAST:
                return map.containsKey(new Position2D(position.getX() + 3, position.getY() - 3));
            case NORTHWEST:
                return map.containsKey(new Position2D(position.getX() - 3, position.getY() - 3));
            case SOUTHEAST:
                return map.containsKey(new Position2D(position.getX() + 3, position.getY() + 3));
            case SOUTHWEST:
                return map.containsKey(new Position2D(position.getX() - 3, position.getY() + 3));
        }
        return false;
    }

    private boolean isPositionValidForDirection2(Map<Position2D, Character> map, Position2D position) {
        return map.containsKey(new Position2D(position.getX() - 1, position.getY() - 1)) &&
                map.containsKey(new Position2D(position.getX() - 1, position.getY() + 1)) &&
                map.containsKey(new Position2D(position.getX() + 1, position.getY() - 1)) &&
                map.containsKey(new Position2D(position.getX() + 1, position.getY() + 1));
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        Map<Position2D, Character> map = new HashMap<>();
        String line;
        long y = 0;
        while ((line = br.readLine()) != null) {
            char[] characters = line.toCharArray();
            for(long x = 0; x < characters.length; x++) {
                map.put(new Position2D(x, y), characters[(int) x]);
            }
            y++;
        }

        long result = 0;
        for (Position2D position: map.keySet()) {
            if (isPositionValidForDirection2(map, position)) {
                result = checkWord2(map, position) ? result + 1: result;
            }
        }

        return result;
    }
}
