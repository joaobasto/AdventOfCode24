package org.example.Day22;

import org.example.AbstractSolver;

import java.io.BufferedReader;
import java.io.IOException;

public class Day22Solver extends AbstractSolver {
    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        String line;
        long result = 0L;
        while ((line = br.readLine()) != null) {
            result += calculateSecretNumber(Long.parseLong(line));
        }
        return result;
    }

    private long calculateSecretNumber(long number) {
        long currentNumber = number;
        for(int i = 0; i < 2000; i++) {
            long aux1 = currentNumber << 6;
            currentNumber = currentNumber ^ aux1;
            currentNumber = currentNumber % 16777216;

            long aux2 = currentNumber >> 5;
            currentNumber = currentNumber ^ aux2;
            currentNumber = currentNumber % 16777216;

            long aux3 = currentNumber << 11;
            currentNumber = currentNumber ^ aux3;
            currentNumber = currentNumber % 16777216;
        }
        return currentNumber;
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        return 0;
    }
}
