package org.example.Day2502;

import org.example.AbstractSolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Day2502Solver extends AbstractSolver {


    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        long result = 0;
        String line;
        line = br.readLine();
        String[] rangeSubstrings = line.split(",");
        for (String rangeSubstring : rangeSubstrings) {
            String[] rangeLimits = rangeSubstring.split("-");
            long rangeMin = Long.parseLong(rangeLimits[0]);
            long rangeMax = Long.parseLong(rangeLimits[1]);

            for (long i = rangeMin; i <= rangeMax; i++) {
                int numberLength = String.valueOf(i).length();
                if (numberLength % 2 == 1) {
                    continue;
                }
                long firstHalf = (long) (i / Math.pow(10, (double) numberLength / 2));
                long secondHalf = (long) (i % Math.pow(10, (double) numberLength / 2));
                if (firstHalf == secondHalf) {
                    result += i;
                }
            }
        }

        return result;
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        long result = 0;
        String line;
        line = br.readLine();
        String[] rangeSubstrings = line.split(",");
        for (String rangeSubstring : rangeSubstrings) {
            String[] rangeLimits = rangeSubstring.split("-");
            long rangeMin = Long.parseLong(rangeLimits[0]);
            long rangeMax = Long.parseLong(rangeLimits[1]);

            for (long i = rangeMin; i <= rangeMax; i++) {
                int numberLength = String.valueOf(i).length();
                List<Integer> divisors = getAllDivisors(numberLength);

                long finalI = i;
                boolean isInvalid = divisors.stream().anyMatch(divisor -> isSequence(finalI, divisor));
                if (isInvalid) {
                    result += i;
                }
            }
        }

        return result;
    }

    private boolean isSequence(long i, int divisor) {
        Set<Long> subresults = new HashSet<>();
        long numberAux = i;
        while (numberAux > 0) {
            long remainder = (long) (numberAux % Math.pow(10, divisor));
            subresults.add(remainder);
            numberAux = (long) (numberAux / Math.pow(10, divisor));
        }
        return subresults.size() == 1;
    }

    private List<Integer> getAllDivisors(int number) {
        List<Integer> result = new ArrayList<>();
        for (int i = 1; i < number; i++) {
            if (number % i == 0) {
                result.add(i);
            }
        }
        return result;
    }
}
