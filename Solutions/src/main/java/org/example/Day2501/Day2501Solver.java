package org.example.Day2501;

import org.example.AbstractSolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2501Solver extends AbstractSolver {


    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        long result = 0;
        String line;
        long currentPosition = 50;
        while ((line = br.readLine()) != null) {
            Pattern pattern = Pattern.compile("(\\w)(\\d+)");
            Matcher matcher = pattern.matcher(line);
            matcher.find();
            String direction = matcher.group(1);
            long step = Long.parseLong(matcher.group(2));

            step = step % 100;
            if (direction.equals("L")) {
                step = - step;
            }

            currentPosition = currentPosition + step;
            currentPosition = currentPosition % 100;
            if (currentPosition < 0) {
                currentPosition = 100 + currentPosition;
            }
            if (currentPosition == 0) {
                result++;
            }
        }

        return result;
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        long result = 0;
        String line;
        long currentPosition = 50;
        while ((line = br.readLine()) != null) {
            Pattern pattern = Pattern.compile("(\\w)(\\d+)");
            Matcher matcher = pattern.matcher(line);
            matcher.find();
            String direction = matcher.group(1);
            long step = Long.parseLong(matcher.group(2));

            result = result + Math.abs(step / 100);
            step = step % 100;
            if (direction.equals("L")) {
                step = - step;
            }

            if (currentPosition > 0 && currentPosition + step < 0) {
                result++;
            } else if (currentPosition + step > 100) {
                result++;
            }

            currentPosition = currentPosition + step;
            currentPosition = currentPosition % 100;
            if (currentPosition < 0) {
                currentPosition = 100 + currentPosition;
            }
            if (currentPosition == 0) {
                result++;
            }
        }

        return result;
    }
}
