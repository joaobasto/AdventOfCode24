package org.example.Day25;

import org.example.AbstractSolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day25Solver extends AbstractSolver {
    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        String line;
        boolean isStart = true;
        boolean isLock = false;
        List<Integer> values = new ArrayList<>(List.of(0, 0, 0, 0, 0));
        List<List<Integer>> locks = new ArrayList<>();
        List<List<Integer>> keys = new ArrayList<>();
        int rowIndex = 1;
        while ((line = br.readLine()) != null) {
            if (line.isEmpty()) {
                isStart = true;
                if (isLock) {
                    locks.add(values);
                } else {
                    keys.add(values.stream().map(value -> 7 - value).collect(Collectors.toList()));
                }
                values = new ArrayList<>(List.of(0, 0, 0, 0, 0));
                continue;
            }
            if (isStart) {
                rowIndex = 1;
                isLock = line.equals("#####");
                isStart = false;
            }
            char[] chars = line.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (isLock && chars[i] == '#') {
                    values.set(i, rowIndex);
                } else if (!isLock && chars[i] == '.') {
                    values.set(i, rowIndex);
                }
            }
            rowIndex++;
        }
        if (isLock) {
            locks.add(values);
        } else {
            keys.add(values.stream().map(value -> 7 - value).collect(Collectors.toList()));
        }

        long result = 0;
        for (List<Integer> lock : locks) {
            for (List<Integer> key : keys) {
                boolean isValid = true;
                for (int i = 0; i < lock.size(); i++) {
                    if (lock.get(i) + key.get(i) > 7) {
                        isValid = false;
                        break;
                    }
                }
                if (isValid) {
                    result++;
                }
            }
        }
        return result;
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        return 0;
    }
}
