package org.example.Day2505;

import org.example.AbstractSolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Day2505Solver extends AbstractSolver {

    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        String line;
        long result = 0;
        boolean isRange = true;
        List<Range> ranges = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            if(line.isBlank()) {
                isRange = false;
                continue;
            }
            if (isRange) {
                String[] rangeLimits = line.split("-");
                Long minLimit = Long.parseLong(rangeLimits[0]);
                Long maxLimit = Long.parseLong(rangeLimits[1]);
                ranges.add(new Range(minLimit, maxLimit));
            } else {
                Long number = Long.parseLong(line);
                if (isInARange(number, ranges)) {
                    result++;
                }
            }
        }
        return result;
    }

    private boolean isInARange(Long number, List<Range> ranges) {
        for (Range range : ranges) {
            if (number >= range.getMinLimit() && number <= range.getMaxLimit()) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        String line;
        long result = 0;
        List<Range> ranges = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            if(line.isBlank()) {
                break;
            }
            String[] rangeLimits = line.split("-");
            Long minLimit = Long.parseLong(rangeLimits[0]);
            Long maxLimit = Long.parseLong(rangeLimits[1]);
            ranges.add(new Range(minLimit, maxLimit));
        }
        NavigableMap<Long, Long> processedRanges = new TreeMap<>();
        for (Range range : ranges) {
            updateProcessedRanges(processedRanges, range);
        }
        return calculateTotalElements(processedRanges);
    }

    private long calculateTotalElements(NavigableMap<Long, Long> processedRanges) {
        long total = 0;
        for (Map.Entry<Long, Long> entry : processedRanges.entrySet()) {
            total = total + (entry.getValue() - entry.getKey() + 1);
        }
        return total;
    }

    private void updateProcessedRanges(NavigableMap<Long, Long> processedRanges, Range range) {
        long currentMinLimit = range.getMinLimit();
        long currentMaxLimit = range.getMaxLimit();

        Long range1MinLimit = processedRanges.floorKey(currentMinLimit);
        if (range1MinLimit != null && processedRanges.get(range1MinLimit) >= currentMinLimit) {
            long auxValue = processedRanges.get(range1MinLimit) + 1;
            if (auxValue <= currentMaxLimit) {
                currentMinLimit = auxValue;
            } else {
                return;
            }
        }

        Long range2MinLimit = processedRanges.floorKey(currentMaxLimit);
        if (range2MinLimit != null && processedRanges.get(range2MinLimit) >= currentMaxLimit) {
            long auxValue = range2MinLimit - 1;
            if (auxValue >= currentMinLimit) {
                currentMaxLimit = auxValue;
            } else {
                return;
            }
        }

        //check if minlimit falls in an existing range
        //if yes:
            //if the max limit + 1 of such existing range in within the current range
                //set min range of current range to max limit of existing range + 1
            //else
                //skip processing this range
        //if not:
            //do nothing (keep the min limit)

        //check if maxlimit falls in an existing range
        //if yes:
            //if the min limit - 1 of such existing range in within the current range
                //set max range of current range to min limit of existing range - 1
            //else
                //skip processing this range
        //if not:
            //do nothing (keep the max limit)

        //delete all existing ranges contained in this new one
        Set<Long> keysToRemove = new HashSet<>();
        for(Map.Entry<Long, Long> entry: processedRanges.entrySet()) {
            if (entry.getKey() >= currentMinLimit && entry.getValue() <= currentMaxLimit) {
                keysToRemove.add(entry.getKey());
            }
        }
        for(Long number: keysToRemove) {
            processedRanges.remove(number);
        }

        //add this range if it is valid
        if (currentMaxLimit >= currentMinLimit) {
            processedRanges.put(currentMinLimit, currentMaxLimit);
        }
    }

}
