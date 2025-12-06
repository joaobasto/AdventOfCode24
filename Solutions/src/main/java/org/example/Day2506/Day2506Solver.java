package org.example.Day2506;

import org.example.AbstractSolver;
import org.example.utils.Grid;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Day2506Solver extends AbstractSolver {

    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        String line;
        Grid<Long> grid = new Grid<>();
        List<String> operators = new ArrayList<>();
        long y = 0;
        while ((line = br.readLine()) != null) {
            String[] subStrings = line.trim().split("\\s+");
            if (subStrings[0].equals("+") || subStrings[0].equals("*")) {
                operators.addAll(Arrays.asList(subStrings));
                break;
            }
            for(long x = 0; x < subStrings.length; x++) {
                grid.addElement(x, y, Long.parseLong(subStrings[Math.toIntExact(x)]));
            }
            grid.setNumberOfColumns(subStrings.length);
            y++;
        }
        grid.setNumberOfRows(y);

        long result = 0;
        //process the operations
        for (int i = 0; i < grid.getNumberOfColumns(); i++) {
            String operator = operators.get(i);
            long subResult = 0;
            if (operator.equals("+")) {
                for (int j = 0; j < grid.getNumberOfRows(); j++) {
                    subResult = subResult + grid.getElement(i, j);
                }
            }
            if (operator.equals("*")) {
                subResult = 1;
                for (int j = 0; j < grid.getNumberOfRows(); j++) {
                    subResult = subResult * grid.getElement(i, j);
                }
            }
            result += subResult;
        }
        return result;
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        String line;
        Grid<Long> grid = new Grid<>();
        List<String> operators = new ArrayList<>();
        List<String> numberLines = new ArrayList<>();
        long y = 0;
        while ((line = br.readLine()) != null) {
            String[] subStrings = line.trim().split("\\s+");
            if (subStrings[0].equals("+") || subStrings[0].equals("*")) {
                operators.addAll(Arrays.asList(subStrings));
                break;
            }
            numberLines.add(line);
            for(long x = 0; x < subStrings.length; x++) {
                grid.addElement(x, y, Long.parseLong(subStrings[Math.toIntExact(x)]));
            }
            grid.setNumberOfColumns(subStrings.length);
            y++;
        }
        grid.setNumberOfRows(y);

        //get length of every column
        Map<Long, Long> lengthByColumn = new HashMap<>();
        Map<Long, Long> startingPositionByColumn = new HashMap<>();
        long startingPosition = 0;
        for (int i = 0; i < grid.getNumberOfColumns(); i++) {
            long max = 0;
            for (int j = 0; j < grid.getNumberOfRows(); j++) {
                if (grid.getElement(i, j) > max) {
                    max = grid.getElement(i, j);
                }
            }
            lengthByColumn.put((long) i, (long) String.valueOf(max).length());
            startingPositionByColumn.put((long) i, startingPosition);
            startingPosition = startingPosition + (long) String.valueOf(max).length() + 1;
        }

        //process numbers in each column
        Map<Long, List<Long>> numbersByColumn = new HashMap<>();
        for(Map.Entry<Long, Long> entry : lengthByColumn.entrySet()) {
            long columnIndex = entry.getKey();
            long columnLength = entry.getValue();
            long columnStartingPosition = startingPositionByColumn.get(columnIndex);
            Long[][] values = new Long[Math.toIntExact(columnLength)][Math.toIntExact(grid.getNumberOfRows())];
            for (int i = 0; i < numberLines.size(); i++) {
                for (long j = columnStartingPosition; j < columnStartingPosition + columnLength; j++) {
                    char c = numberLines.get(i).toCharArray()[Math.toIntExact(j)];
                    if (Character.getNumericValue(c) > 0) {
                        values[Math.toIntExact(j - columnStartingPosition)][i] = (long) Character.getNumericValue(c);
                    }
                }
            }
            List<Long> numbers = new ArrayList<>();
            for (int i = 0; i < columnLength; i++) {
                long number = 0;
                for (int j = 0; j < grid.getNumberOfRows(); j++) {
                    if (values[i][j] != null) {
                        number = number * 10 + values[i][j];
                    }
                }
                numbers.add(number);
            }
            numbersByColumn.put(columnIndex, numbers);
        }

        long result = 0;
        //process the operations
        for (long i = 0; i < grid.getNumberOfColumns(); i++) {
            String operator = operators.get((int) i);
            long subResult = 0;
            List<Long> operands = numbersByColumn.get(i);
            if (operator.equals("+")) {
                for (Long operand : operands) {
                    subResult = subResult + operand;
                }
            }
            if (operator.equals("*")) {
                subResult = 1;
                for (Long operand : operands) {
                    subResult = subResult * operand;
                }
            }
            result += subResult;
        }
        return result;
    }

}
