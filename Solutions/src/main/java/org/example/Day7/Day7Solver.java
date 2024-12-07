package org.example.Day7;

import org.example.AbstractSolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day7Solver extends AbstractSolver {

    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        String line;
        long result = 0;
        while ((line = br.readLine()) != null) {
            String[] numbersAsStrings = line.split(": ");
            Long testValue = Long.valueOf(numbersAsStrings[0]);
            List<Long> numbers = Arrays.stream(numbersAsStrings[1].split("\\s+"))
                    .map(Long::valueOf).collect(Collectors.toList());

            //define initial state
            long currentValue = numbers.get(0);
            int currentNumberIndex = 1;

            if (isValidEquation(currentValue, currentNumberIndex, testValue, numbers)) {
                result += testValue;
            }
        }

        return result;
    }

    private boolean isValidEquation(long currentValue, int currentNumberIndex, long testValue, List<Long> numbers) {
        //check for terminal conditions
        if (currentValue > testValue) {
            return false;
        }
        if (currentNumberIndex == numbers.size()) {
            return currentValue == testValue;
        }
        return isValidEquation(currentValue * numbers.get(currentNumberIndex), currentNumberIndex+1, testValue, numbers)
                || isValidEquation(currentValue + numbers.get(currentNumberIndex), currentNumberIndex+1, testValue, numbers);
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
        }

        long result = 0;

        return result;
    }
}
