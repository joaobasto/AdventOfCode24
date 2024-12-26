package org.example.Day21;

import org.example.AbstractSolver;
import org.example.utils.Position2D;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Day21Solver extends AbstractSolver {

    //To solve exercise 1 or 2, this should be set to 2 for exercise 1 and 25 for exercise 2
    private static final int NUMBER_OF_DIRECTIONAL_KEYPADS = 25;

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
            TreeNode rootNode = new TreeNode(line, true, 0);
            char[] characters = line.toCharArray();
            char currentCharacter = 'A';
            for (int i = 0; i < characters.length; i++) {
                TreeNode treeNode = new TreeNode(currentCharacter + String.valueOf(characters[i]), false, 1);
                rootNode.addChild(treeNode);
                currentCharacter = characters[i];
            }
            //for all children
                //calculate the two possible ways and add them as child nodes
            List<TreeNode> allChildren = new ArrayList<>();
            for (TreeNode treeNode : rootNode.getChildren()) {
                Set<String> values = calculatePossibleWays(treeNode.value);
                for (String value : values) {
                    TreeNode childNode = new TreeNode(value, true, 2);
                    treeNode.addChild(childNode);
                    allChildren.add(childNode);
                }
            }

            int level = 3;
            Map<CacheKey, Long> sizeByKey = new HashMap<>();
            List<TreeNode> newChildren;
            for (int j = 0; j < NUMBER_OF_DIRECTIONAL_KEYPADS; j++) {
                //for all children
                //split them into the sequence substrings
                newChildren = new ArrayList<>();
                for (TreeNode treeNode : allChildren) {
                    if (sizeByKey.containsKey(new CacheKey(treeNode.value, level))) {
                        continue;
                    }
                    currentCharacter = 'A';
                    for (int i = 0; i < treeNode.value.length(); i++) {
                        TreeNode childNode = new TreeNode(currentCharacter + String.valueOf(treeNode.value.charAt(i)), false, level + 1);
                        treeNode.addChild(childNode);
                        newChildren.add(childNode);
                        currentCharacter = treeNode.value.charAt(i);
                    }
                    sizeByKey.put(new CacheKey(treeNode.value, level), 0L);
                }
                level++;

                //for all children
                //calculate the two possible ways and add them as child nodes
                allChildren = new ArrayList<>();
                for (TreeNode treeNode : newChildren) {
                    if (sizeByKey.containsKey(new CacheKey(treeNode.value, level))) {
                        continue;
                    }
                    Set<String> values = calculatePossibleWays2(treeNode.value);
                    for (String value : values) {
                        TreeNode childNode = new TreeNode(value, true, level + 1);
                        treeNode.addChild(childNode);
                        allChildren.add(childNode);
                    }
                    sizeByKey.put(new CacheKey(treeNode.value, level), 0L);
                }
                level++;
            }

            result += (rootNode.calculateSize(sizeByKey) * Long.parseLong(line.substring(0, 3)));
        }
        return result;
    }

    private Set<String> calculatePossibleWays(String value) {
        Set<String> result = new HashSet<>();
        char fromValue = value.charAt(0);
        char toValue = value.charAt(1);
        long rightDistance = NUMERIC_KEYPAD_POSITIONS.get(toValue).getX() -
                NUMERIC_KEYPAD_POSITIONS.get(fromValue).getX();
        long downDistance = NUMERIC_KEYPAD_POSITIONS.get(toValue).getY() -
                NUMERIC_KEYPAD_POSITIONS.get(fromValue).getY();
        //check if we will go right, and calculate moves if we will
        //check if we will go up, and calculate moves if we will
        //check if we will go down, and calculate moves if we will
        //else
        //check if we will go up, and calculate moves if we will
        //check if we will go down, and calculate moves if we will
        //check if we will go left, and calculate moves if we will
        StringBuilder stringBuilderHV = new StringBuilder();
        if (rightDistance > 0) {
            for (int j = 0; j < rightDistance; j++) {
                stringBuilderHV.append('>');
            }
        }
        if (rightDistance < 0) {
            for (int j = 0; j < -rightDistance; j++) {
                stringBuilderHV.append('<');
            }
        }
        if (downDistance > 0) {
            for (int j = 0; j < downDistance; j++) {
                stringBuilderHV.append('v');
            }
        } else {
            for (int j = 0; j < -downDistance; j++) {
                stringBuilderHV.append('^');
            }
        }
        stringBuilderHV.append('A');
        if (!(NUMERIC_KEYPAD_POSITIONS.get(fromValue).getY() >= 3 && NUMERIC_KEYPAD_POSITIONS.get(toValue).getX() == 0)) {
           result.add(stringBuilderHV.toString());
        }
        StringBuilder stringBuilderVH = new StringBuilder();
        if (downDistance > 0) {
            for (int j = 0; j < downDistance; j++) {
                stringBuilderVH.append('v');
            }
        } else {
            for (int j = 0; j < -downDistance; j++) {
                stringBuilderVH.append('^');
            }
        }
        if (rightDistance > 0) {
            for (int j = 0; j < rightDistance; j++) {
                stringBuilderVH.append('>');
            }
        }
        if (rightDistance < 0) {
            for (int j = 0; j < -rightDistance; j++) {
                stringBuilderVH.append('<');
            }
        }
        stringBuilderVH.append('A');
        if (!(NUMERIC_KEYPAD_POSITIONS.get(toValue).getY() >= 3 && NUMERIC_KEYPAD_POSITIONS.get(fromValue).getX() == 0)) {
            result.add(stringBuilderVH.toString());
        }
        return result;
    }

    private Set<String> calculatePossibleWays2(String value) {
        Set<String> result = new HashSet<>();
        char fromValue = value.charAt(0);
        char toValue = value.charAt(1);
        long rightDistance = DIRECTIONAL_KEYPAD_POSITIONS.get(toValue).getX() -
                DIRECTIONAL_KEYPAD_POSITIONS.get(fromValue).getX();
        long downDistance = DIRECTIONAL_KEYPAD_POSITIONS.get(toValue).getY() -
                DIRECTIONAL_KEYPAD_POSITIONS.get(fromValue).getY();
        //check if we will go right, and calculate moves if we will
        //check if we will go up, and calculate moves if we will
        //check if we will go down, and calculate moves if we will
        //else
        //check if we will go up, and calculate moves if we will
        //check if we will go down, and calculate moves if we will
        //check if we will go left, and calculate moves if we will
        StringBuilder stringBuilderHV = new StringBuilder();
        if (rightDistance > 0) {
            for (int j = 0; j < rightDistance; j++) {
                stringBuilderHV.append('>');
            }
        }
        if (rightDistance < 0) {
            for (int j = 0; j < -rightDistance; j++) {
                stringBuilderHV.append('<');
            }
        }
        if (downDistance > 0) {
            for (int j = 0; j < downDistance; j++) {
                stringBuilderHV.append('v');
            }
        } else {
            for (int j = 0; j < -downDistance; j++) {
                stringBuilderHV.append('^');
            }
        }
        stringBuilderHV.append('A');
        if (!(DIRECTIONAL_KEYPAD_POSITIONS.get(fromValue).getY() == 0 && DIRECTIONAL_KEYPAD_POSITIONS.get(toValue).getX() == 0)) {
            result.add(stringBuilderHV.toString());
        }
        StringBuilder stringBuilderVH = new StringBuilder();
        if (downDistance > 0) {
            for (int j = 0; j < downDistance; j++) {
                stringBuilderVH.append('v');
            }
        } else {
            for (int j = 0; j < -downDistance; j++) {
                stringBuilderVH.append('^');
            }
        }
        if (rightDistance > 0) {
            for (int j = 0; j < rightDistance; j++) {
                stringBuilderVH.append('>');
            }
        }
        if (rightDistance < 0) {
            for (int j = 0; j < -rightDistance; j++) {
                stringBuilderVH.append('<');
            }
        }
        stringBuilderVH.append('A');
        if (!(DIRECTIONAL_KEYPAD_POSITIONS.get(toValue).getY() == 0 && DIRECTIONAL_KEYPAD_POSITIONS.get(fromValue).getX() == 0)) {
            result.add(stringBuilderVH.toString());
        }
        return result;
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        return 0;
    }
}
