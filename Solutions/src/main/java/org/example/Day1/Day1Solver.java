package org.example.Day1;

import org.example.AbstractSolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day1Solver extends AbstractSolver {


    @Override
    protected int createSolutionExercise1(BufferedReader br) throws IOException {
        String line;
        List<Integer> leftNumbers = new ArrayList<>();
        List<Integer> rightNumbers = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            String[] numbersAsStrings = line.split("\\s+");
            List<Integer> numbers = Arrays.stream(numbersAsStrings)
                    .map(Integer::valueOf).collect(Collectors.toList());
            leftNumbers.add(numbers.get(0));
            rightNumbers.add(numbers.get(1));
        }
        leftNumbers.sort(Integer::compareTo);
        rightNumbers.sort(Integer::compareTo);

        int result = 0;
        for (int i = 0; i < leftNumbers.size(); i++) {
            result += Math.abs(leftNumbers.get(i) - rightNumbers.get(i));
        }

        return result;
    }

    @Override
    protected int createSolutionExercise2(BufferedReader br) throws IOException {
        String line;
        List<Integer> leftNumbers = new ArrayList<>();
        Map<Integer, Integer> occurencesPerNumberRight = new HashMap<>();
        while ((line = br.readLine()) != null) {
            String[] numbersAsStrings = line.split("\\s+");
            List<Integer> numbers = Arrays.stream(numbersAsStrings)
                    .map(Integer::valueOf).collect(Collectors.toList());
            leftNumbers.add(numbers.get(0));
            occurencesPerNumberRight.compute(numbers.get(1), (k, v) -> v == null ? 1 : v + 1);
        }

        int result = 0;
        for (Integer leftNumber : leftNumbers) {
            int occurences = occurencesPerNumberRight.getOrDefault(leftNumber, 0);
            result += occurences * leftNumber;
        }

        return result;
    }
}
