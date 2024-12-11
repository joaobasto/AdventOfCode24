package org.example.Day11;

import org.example.AbstractSolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day11Solver extends AbstractSolver {

    private static final long N_BLINKS = 25;
    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        String line = br.readLine();

        String[] numbersAsStrings = line.split("\\s+");
        List<Long> numbers = Arrays.stream(numbersAsStrings)
                .map(Long::valueOf).collect(Collectors.toList());

        for (long i = 0; i < N_BLINKS; i++) {
            processBlink(numbers);
        }

        long result = numbers.size();
        return result;
    }

    private void processBlink(List<Long> numbers) {
        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.get(i).equals(0L)) {
                numbers.set(i, 1L);
            } else if (String.valueOf(numbers.get(i)).length()%2==0) {
                long halfOfNumberOfDigits = String.valueOf(numbers.get(i)).length()/2;
                long firstNumber = numbers.get(i) / (long) Math.pow(10,halfOfNumberOfDigits);
                long secondNumber = numbers.get(i) % (long) Math.pow(10,halfOfNumberOfDigits);
                numbers.set(i, secondNumber);
                numbers.add(i, firstNumber);
                i++;
            } else {
                numbers.set(i, numbers.get(i)*2024);
            }
        }
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        return 0;
    }
}
