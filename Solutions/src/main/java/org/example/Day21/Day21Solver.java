package org.example.Day21;

import org.example.AbstractSolver;
import org.example.utils.Position2D;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day21Solver extends AbstractSolver {

    private static final Map<Character, Position2D> NUMERIC_KEYPAD_POSITIONS =
            createNumericKeypadPositions();

    private static final Map<Character, Position2D> DIRECTIONAL_KEYPAD_POSITIONS =
            createDirectionalKeypadPositions();

    private static Map<Character, Position2D> createNumericKeypadPositions() {
        Map<Character, Position2D> map = new HashMap<>();
        map.put('7', new Position2D(0,0));
        map.put('8', new Position2D(1,0));
        map.put('9', new Position2D(2,0));
        map.put('4', new Position2D(0,1));
        map.put('5', new Position2D(1,1));
        map.put('6', new Position2D(2,1));
        map.put('1', new Position2D(0,2));
        map.put('2', new Position2D(1,2));
        map.put('3', new Position2D(2,2));
        map.put('0', new Position2D(1,3));
        map.put('A', new Position2D(2,3));
        return map;
    }

    private static Map<Character, Position2D> createDirectionalKeypadPositions() {
        Map<Character, Position2D> map = new HashMap<>();
        map.put('^', new Position2D(1,0));
        map.put('A', new Position2D(2,0));
        map.put('<', new Position2D(0,1));
        map.put('v', new Position2D(1,1));
        map.put('>', new Position2D(2,1));
        return map;
    }

    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        long result = 0L;
        String line;
        while ((line = br.readLine()) != null) {
            List<Character> level2Characters = new ArrayList<>();
            char currentCharacter = 'A';
            char[] characters = line.toCharArray();
            for (int i = 0; i < characters.length; i++) {
                long rightDistance = NUMERIC_KEYPAD_POSITIONS.get(characters[i]).getX() -
                        NUMERIC_KEYPAD_POSITIONS.get(currentCharacter).getX();
                long downDistance = NUMERIC_KEYPAD_POSITIONS.get(characters[i]).getY() -
                        NUMERIC_KEYPAD_POSITIONS.get(currentCharacter).getY();
                //check if we will go right, and calculate moves if we will
                    //check if we will go up, and calculate moves if we will
                    //check if we will go down, and calculate moves if we will
                //else
                    //check if we will go up, and calculate moves if we will
                    //check if we will go down, and calculate moves if we will
                    //check if we will go left, and calculate moves if we will
                if (rightDistance > 0) {
                    for (int j = 0; j < rightDistance; j++) {
                        level2Characters.add('>');
                    }
                }
                if (rightDistance < 0 && NUMERIC_KEYPAD_POSITIONS.get(currentCharacter).getY() < 3) {
                    for (int j = 0; j < -rightDistance; j++) {
                        level2Characters.add('<');
                    }
                }
                if (downDistance > 0) {
                    for (int j = 0; j < downDistance; j++) {
                        level2Characters.add('v');
                    }
                } else {
                    for (int j = 0; j < -downDistance; j++) {
                        level2Characters.add('^');
                    }
                }
                if (rightDistance < 0 && NUMERIC_KEYPAD_POSITIONS.get(currentCharacter).getY() >= 3) {
                    for (int j = 0; j < -rightDistance; j++) {
                        level2Characters.add('<');
                    }
                }
                //press A
                level2Characters.add('A');
                //update current character
                currentCharacter = characters[i];
            }
            List<Character> level3Characters = processLevel2Characters(level2Characters);
            List<Character> level4Characters = processLevel2Characters(level3Characters);
            result += (level4Characters.size() * Long.parseLong(line.substring(0, 3)));
            int debug = 1;
        }
        return result;
    }

    private List<Character> processLevel2Characters(List<Character> level2Characters) {
        List<Character> level3Characters = new ArrayList<>();
        char currentCharacter = 'A';
        for(int i = 0; i < level2Characters.size(); i++) {
            long rightDistance = DIRECTIONAL_KEYPAD_POSITIONS.get(level2Characters.get(i)).getX() -
                    DIRECTIONAL_KEYPAD_POSITIONS.get(currentCharacter).getX();
            long downDistance = DIRECTIONAL_KEYPAD_POSITIONS.get(level2Characters.get(i)).getY() -
                    DIRECTIONAL_KEYPAD_POSITIONS.get(currentCharacter).getY();
            //check if we will go right, and calculate moves if we will
            //check if we will go up, and calculate moves if we will
            //check if we will go down, and calculate moves if we will
            //else
            //check if we will go up, and calculate moves if we will
            //check if we will go down, and calculate moves if we will
            //check if we will go left, and calculate moves if we will
            if (rightDistance > 0) {
                for (int j = 0; j < rightDistance; j++) {
                    level3Characters.add('>');
                }
            }
            if (rightDistance < 0 && downDistance <= 0) {
                for (int j = 0; j < -rightDistance; j++) {
                    level3Characters.add('<');
                }
            }
            if (downDistance > 0) {
                for (int j = 0; j < downDistance; j++) {
                    level3Characters.add('v');
                }
            } else {
                for (int j = 0; j < -downDistance; j++) {
                    level3Characters.add('^');
                }
            }
            if (rightDistance < 0 && downDistance > 0) {
                for (int j = 0; j < -rightDistance; j++) {
                    level3Characters.add('<');
                }
            }
            //press A
            level3Characters.add('A');
            //update current character
            currentCharacter = level2Characters.get(i);
        }
        return level3Characters;
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        return 0;
    }
}
