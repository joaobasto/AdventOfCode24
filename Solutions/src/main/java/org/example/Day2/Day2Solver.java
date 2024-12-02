package org.example.Day2;

import org.example.AbstractSolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day2Solver extends AbstractSolver {

    private static final int ALLOWED_REMOVALS = 1;


    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        String line;
        long result = 0;
        while ((line = br.readLine()) != null) {
            String[] levelsAsStrings = line.split("\\s+");
            List<Long> levels = Arrays.stream(levelsAsStrings)
                    .map(Long::valueOf).collect(Collectors.toList());
            if (isRecordValid(levels)) {
                result++;
            }
        }
        return result;
    }

    private boolean isRecordValid(List<Long> levels) {
        //check if sequence should be increasing or decreasing
        boolean isIncreasing = false;
        long diff = levels.get(1) - levels.get(0);
        if (diff == 0 || Math.abs(diff) >= 4) {
            return false;
        } else if (diff > 0) {
            isIncreasing = true;
        }

        for(int i = 1; i < levels.size() -1; i++) {
            diff = levels.get(i + 1) - levels.get(i);
            if (diff == 0 || Math.abs(diff) >= 4 || (isIncreasing && diff < 0) || (!isIncreasing && diff > 0)) {
                return false;
            }
        }

        return true;
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        String line;
        long result = 0;
        while ((line = br.readLine()) != null) {
            String[] levelsAsStrings = line.split("\\s+");
            List<Long> levels = Arrays.stream(levelsAsStrings)
                    .map(Long::valueOf).collect(Collectors.toList());
            if (isRecordValid(levels)) {
                result++;
                continue;
            }
            boolean isRecordValidWithRemovalOfOneElement = IntStream.rangeClosed(0, levels.size() - 1).boxed()
                    .anyMatch(value -> isRecordValidWithRemovalOfElementAtPos(levels, value));

            if (isRecordValidWithRemovalOfOneElement) {
                result++;
            }
        }
        return result;
    }

    /**
     * Determines a record is valid after the removal of the element in index pos.
     * @param levels
     * @param pos
     * @return true if the record is valid after the removal of the element in index pos, false otherwise.
     */
    private boolean isRecordValidWithRemovalOfElementAtPos(List<Long> levels, int pos) {
        List<Long> tempLevels = new ArrayList<>(levels);
        tempLevels.remove(pos);
        return isRecordValid(tempLevels);
    }
}
