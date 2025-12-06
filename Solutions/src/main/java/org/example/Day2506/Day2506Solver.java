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
        long result = 0;
        while ((line = br.readLine()) != null) {

        }
        return 0;
    }

}
