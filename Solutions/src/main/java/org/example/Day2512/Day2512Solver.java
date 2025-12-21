package org.example.Day2512;

import org.example.AbstractSolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2512Solver extends AbstractSolver {


    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        String line;
        long result = 0;
        while ((line = br.readLine()) != null) {
            if (line.contains("x")) {
                // Split dimensions and boxes
                String[] parts = line.split(":");
                if (parts.length != 2) {
                    continue; // malformed line
                }

                String dimensions = parts[0].trim();
                String boxesPart = parts[1].trim();

                // Parse width and height
                String[] dimParts = dimensions.split("x");
                if (dimParts.length != 2) {
                    continue; // malformed dimensions
                }

                int w = Integer.parseInt(dimParts[0]);
                int h = Integer.parseInt(dimParts[1]);

                int area = (w / 3) * (h / 3);

                // Sum box counts
                int totalBoxes = 0;
                String[] boxes = boxesPart.split("\\s+");
                for (String box : boxes) {
                    totalBoxes += Integer.parseInt(box);
                }

                if (totalBoxes <= area) {
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
