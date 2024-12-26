package org.example.Day22;

import org.example.AbstractSolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Day22Solver extends AbstractSolver {
    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        String line;
        long result = 0L;
        Map<List<Long>, Long> valueMap = new HashMap<>();
        while ((line = br.readLine()) != null) {
            result += calculateSecretNumber(Long.parseLong(line), valueMap);
        }
        return result;
    }

    private long calculateSecretNumber(long number, Map<List<Long>, Long> valueMap) {
        long currentNumber = number;
        List<Long> priceDiffs = new ArrayList<>();
        Set<List<Long>> sequenceSet = new HashSet<>();
        for(int i = 0; i < 2000; i++) {
            long oldNumber = currentNumber;
            long aux1 = currentNumber << 6;
            currentNumber = currentNumber ^ aux1;
            currentNumber = currentNumber % 16777216;

            long aux2 = currentNumber >> 5;
            currentNumber = currentNumber ^ aux2;
            currentNumber = currentNumber % 16777216;

            long aux3 = currentNumber << 11;
            currentNumber = currentNumber ^ aux3;
            currentNumber = currentNumber % 16777216;
            priceDiffs.add((currentNumber % 10) - (oldNumber % 10));
            if (priceDiffs.size() > 4) {
                priceDiffs.remove(0);
            }
            if (priceDiffs.size() == 4 && !sequenceSet.contains(priceDiffs)) {
                final long auxValue = currentNumber;
                valueMap.compute(new ArrayList<>(priceDiffs), (k, v) -> v == null ? auxValue % 10 : v + (auxValue % 10));
                sequenceSet.add(new ArrayList<>(priceDiffs));
            }
        }
        return currentNumber;
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        String line;
        long result = 0L;
        Map<List<Long>, Long> valueMap = new HashMap<>();
        while ((line = br.readLine()) != null) {
            result += calculateSecretNumber(Long.parseLong(line), valueMap);
        }
        return valueMap.values().stream().max(Long::compareTo).get();
    }
}
