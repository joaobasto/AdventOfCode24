package org.example.Day2503;

import org.example.AbstractSolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2503Solver extends AbstractSolver {


    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        long result = 0;
        String line;
        while ((line = br.readLine()) != null) {
            char[] chars = line.toCharArray();
            List<Integer> numbers = new ArrayList<>();
            for (char aChar : chars) {
                numbers.add(Character.getNumericValue(aChar));
            }
            result += solve(numbers, 0, null);
        }

        return result;
    }

    private long solve(List<Integer> numbers, int i, Integer stateValue) {
        if (i == numbers.size()) {
            return -1;
        }

        if (stateValue == null) {
            return Math.max(
                    solve(numbers, i+1, stateValue),
                    solve(numbers, i+1, numbers.get(i)));
        }

        return Math.max(
                solve(numbers, i+1, stateValue),
                stateValue * 10 + numbers.get(i));
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        long result = 0;
        String line;
        while ((line = br.readLine()) != null) {
            char[] chars = line.toCharArray();
            List<Integer> numbers = new ArrayList<>();
            for (char aChar : chars) {
                numbers.add(Character.getNumericValue(aChar));
            }
            AtomicLong bestSolution = new AtomicLong(-1);
            result += solve2(numbers, 0, new ArrayList<>(), new HashMap<>(), bestSolution);
        }

        return result;
    }

    private long solve2(List<Integer> numbers, int i, List<Integer> stateValue,
                        Map<CacheKey, Long> cache, AtomicLong bestSolution) {
        if ((bestSolution.get() / ((long)(Math.pow(10, 12 - stateValue.size())))) > getSolution(stateValue)) {
            return -1;
        }
        Long cacheValue = cache.get(new CacheKey(i, stateValue));
        if (cacheValue != null) {
            return cacheValue;
        }

        if (stateValue.size() == 12) {
            long solution = getSolution(stateValue);
            return solution;
        }

        if (i == numbers.size()) {
            return -1;
        }

        List<Integer> newStateValue = new ArrayList<>(stateValue);
        newStateValue.add(numbers.get(i));
        long solution = Math.max(
                solve2(numbers, i+1, stateValue, cache, bestSolution),
                solve2(numbers, i+1, newStateValue, cache, bestSolution));
        cache.put(new CacheKey(i, stateValue), solution);
        if (bestSolution.get() < solution) {
            bestSolution.set(solution);
        }
        return solution;
    }

    private long getSolution(List<Integer> stateValue) {
        long result = 0;

        for (int i = 0; i < stateValue.size(); i++) {
            result = result * 10 + stateValue.get(i);
        }

        return result;
    }
}
