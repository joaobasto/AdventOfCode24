package org.example.Day3;

import org.example.AbstractSolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day3Solver extends AbstractSolver {

    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        long result = 0;
        String line;
        while ((line = br.readLine()) != null) {
            Pattern pattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                result += Long.parseLong(matcher.group(1))*Long.parseLong(matcher.group(2));
            }
        }

        return result;
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        long result = 0;
        String line;
        boolean isEnabled = true;
        while ((line = br.readLine()) != null) {
            Pattern pattern = Pattern.compile("do\\(\\)|mul\\((\\d+),(\\d+)\\)|don't\\(\\)");
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                if (matcher.group().equals("do()")) {
                    isEnabled = true;
                } else if (matcher.group().equals("don't()")) {
                    isEnabled = false;
                } else if (isEnabled) {
                    result += Long.parseLong(matcher.group(1)) * Long.parseLong(matcher.group(2));
                }
            }
        }

        return result;
    }
}
