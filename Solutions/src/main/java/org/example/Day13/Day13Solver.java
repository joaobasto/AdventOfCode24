package org.example.Day13;

import org.example.AbstractSolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day13Solver extends AbstractSolver {

    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        String line;
        long result = 0;
        while ((line = br.readLine()) != null) {
            Pattern pattern = Pattern.compile("Button A: X\\+(\\d+), Y\\+(\\d+)");
            Matcher matcher = pattern.matcher(line);
            matcher.find();
            long xA = Long.parseLong(matcher.group(1));
            long yA = Long.parseLong(matcher.group(2));
            line = br.readLine();
            pattern = Pattern.compile("Button B: X\\+(\\d+), Y\\+(\\d+)");
            matcher = pattern.matcher(line);
            matcher.find();
            long xB = Long.parseLong(matcher.group(1));
            long yB = Long.parseLong(matcher.group(2));
            line = br.readLine();
            pattern = Pattern.compile("Prize: X=(\\d+), Y=(\\d+)");
            matcher = pattern.matcher(line);
            matcher.find();
            long xT = Long.parseLong(matcher.group(1));
            long yT = Long.parseLong(matcher.group(2));

            if (yA * xB == yB * xA) {
                if (xT == yT) {
                    for (int i = 0; i <= 100; i++) {
                        long nA = i;
                        double nB = ((double)yT - nA*yA)/yB;
                        if ((yT - nA*yA) % yB == 0 && nB >= 0) {
                            result += nA*3 + nB;
                            break;
                        }
                    }
                }
            } else {
                double nA = (double) (xT * yB - yT * xB) / (xA * yB - yA * xB);
                double nB = ((double)yT - nA*yA)/yB;
                if (nA >= 0 && nB >= 0 && (xT * yB - yT * xB) % (xA * yB - yA * xB) == 0 && (yT - nA*yA) % yB == 0) {
                    result += nA*3 + nB;
                }
            }
            br.readLine();
        }
        return result;
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        String line;
        long result = 0;
        while ((line = br.readLine()) != null) {
            Pattern pattern = Pattern.compile("Button A: X\\+(\\d+), Y\\+(\\d+)");
            Matcher matcher = pattern.matcher(line);
            matcher.find();
            long xA = Long.parseLong(matcher.group(1));
            long yA = Long.parseLong(matcher.group(2));
            line = br.readLine();
            pattern = Pattern.compile("Button B: X\\+(\\d+), Y\\+(\\d+)");
            matcher = pattern.matcher(line);
            matcher.find();
            long xB = Long.parseLong(matcher.group(1));
            long yB = Long.parseLong(matcher.group(2));
            line = br.readLine();
            pattern = Pattern.compile("Prize: X=(\\d+), Y=(\\d+)");
            matcher = pattern.matcher(line);
            matcher.find();
            long xT = Long.parseLong(matcher.group(1)) + 10000000000000L;
            long yT = Long.parseLong(matcher.group(2)) + 10000000000000L;

            if (yA * xB == yB * xA) {
                if (xT == yT) {
                    for (int i = 0; i <= 100; i++) {
                        long nA = i;
                        double nB = ((double)yT - nA*yA)/yB;
                        if ((yT - nA*yA) % yB == 0 && nB >= 0) {
                            result += nA*3 + nB;
                            break;
                        }
                    }
                }
            } else {
                double nA = (double) (xT * yB - yT * xB) / (xA * yB - yA * xB);
                double nB = ((double)yT - nA*yA)/yB;
                if (nA >= 0 && nB >= 0 && (xT * yB - yT * xB) % (xA * yB - yA * xB) == 0 && (yT - nA*yA) % yB == 0) {
                    result += nA*3 + nB;
                }
            }
            br.readLine();
        }
        return result;
    }
}
