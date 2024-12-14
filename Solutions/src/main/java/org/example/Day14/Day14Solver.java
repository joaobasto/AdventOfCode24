package org.example.Day14;

import org.example.AbstractSolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14Solver extends AbstractSolver {
    private static final long NUMBER_OF_COLUMNS = 101;
    private static final long NUMBER_OF_ROWS = 103;
    private static final long NUMBER_OF_SECONDS = 100;

    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        String line;
        long result = 0;
        Map<Long, Long> countByPosition = new HashMap<>();
        while ((line = br.readLine()) != null) {
            Pattern pattern = Pattern.compile("p=([-\\d]+),([-\\d]+) v=([-\\d]+),([-\\d]+)");
            Matcher matcher = pattern.matcher(line);
            matcher.find();
            long pX = Long.parseLong(matcher.group(1));
            long pY = Long.parseLong(matcher.group(2));
            long vX = Long.parseLong(matcher.group(3));
            vX = vX >= 0 ? vX : NUMBER_OF_COLUMNS + vX;
            long vY = Long.parseLong(matcher.group(4));
            vY = vY >= 0 ? vY : NUMBER_OF_ROWS + vY;

            pX = pX + NUMBER_OF_SECONDS * vX;
            pX = pX % NUMBER_OF_COLUMNS;
            pY = pY + NUMBER_OF_SECONDS * vY;
            pY = pY % NUMBER_OF_ROWS;

            Long quadrant = 0L;
            //determining the quadrant (1 - NW, 2 - NE, 3 - SE, 4 - SW)
            if (pX < NUMBER_OF_COLUMNS/2L && pY < NUMBER_OF_ROWS/2L) {
                quadrant = 1L;
            } else if (pX > NUMBER_OF_COLUMNS/2L && pY < NUMBER_OF_ROWS/2L) {
                quadrant = 2L;
            } else if (pX > NUMBER_OF_COLUMNS/2L && pY > NUMBER_OF_ROWS/2L) {
                quadrant = 3L;
            } else if (pX < NUMBER_OF_COLUMNS/2L && pY > NUMBER_OF_ROWS/2L) {
                quadrant = 4L;
            }
            if (quadrant != 0L) {
                countByPosition.merge(quadrant, 1L, Long::sum);
            }
        }
        result = 1L;
        for (Long value : countByPosition.values()) {
            result = result * value;
        }
        return result;
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        return 0;
    }
}
