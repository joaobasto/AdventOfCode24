package org.example.Day2510;


import org.example.AbstractSolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2510Solver extends AbstractSolver {

    private static final Pattern BRACKETS = Pattern.compile("\\[(.*?)\\]");
    private static final Pattern PARENS   = Pattern.compile("\\((.*?)\\)");
    private static final Pattern BRACES   = Pattern.compile("\\{(.*?)\\}");

    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        long result = 0;
        String line;
        while ((line = br.readLine()) != null) {
            List<Boolean> lights = parseBooleanList(line);
            List<List<Long>> buttons = parseParenValues(line);
            List<Long> joltage = parseBraceValues(line);
            List<Boolean> state = new ArrayList<>();
            for (int i = 0; i < lights.size(); i++) {
                state.add(false);
            }
            result += solve(lights, buttons, 0, state);
        }
        return result;
    }

    private long solve(List<Boolean> lights, List<List<Long>> buttons, long i, List<Boolean> state) {
        if (i == buttons.size()) {
            if (state.equals(lights)) {
                return 0;
            } else {
                return buttons.size() + 1;
            }
        }

        //get state after switching this
        List<Boolean> newState = new ArrayList<>(state);
        for (Long pos : buttons.get((int) i)) {
            newState.set(Math.toIntExact(pos), !newState.get(Math.toIntExact(pos)));
        }

        return Math.min(1 + solve(lights, buttons, i + 1, newState), solve(lights, buttons, i + 1, state));
    }

    private static List<Boolean> parseBooleanList(String line) {
        Matcher m = BRACKETS.matcher(line);
        if (m.find()) {
            String content = m.group(1);
            List<Boolean> result = new ArrayList<>();
            for (char c : content.toCharArray()) {
                if (c == '.') result.add(false);
                else if (c == '#') result.add(true);
            }
            return result;
        }
        return Collections.emptyList();
    }

    private static List<List<Long>> parseParenValues(String line) {
        Matcher m = PARENS.matcher(line);
        List<List<Long>> result = new ArrayList<>();

        while (m.find()) {
            String content = m.group(1).trim();
            if (content.isEmpty()) continue;

            List<Long> numbers = new ArrayList<>();
            for (String part : content.split(",")) {
                numbers.add(Long.parseLong(part.trim()));
            }
            result.add(numbers);
        }
        return result;
    }

    private static List<Long> parseBraceValues(String line) {
        Matcher m = BRACES.matcher(line);
        if (m.find()) {
            String content = m.group(1).trim();
            List<Long> numbers = new ArrayList<>();
            for (String part : content.split(",")) {
                numbers.add(Long.parseLong(part.trim()));
            }
            return numbers;
        }
        return Collections.emptyList();
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        return 0;
    }
}
