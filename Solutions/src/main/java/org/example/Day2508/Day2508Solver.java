package org.example.Day2508;

import org.example.AbstractSolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day2508Solver extends AbstractSolver {

    private static final long NUMBER_OF_CONNECTIONS = 1000;

    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        long result = 0;
        String line;
        long id = 0;
        Set<JunctionBox> junctionBoxes = new HashSet<>();
        Map<Set<JunctionBox>, Double> distances = new HashMap<>();
        while ((line = br.readLine()) != null) {
            String[] coordinates = line.split(",");
            JunctionBox newJunctionBox = new JunctionBox(id,
                    Long.parseLong(coordinates[0]),
                    Long.parseLong(coordinates[1]),
                    Long.parseLong(coordinates[2]));
            for (JunctionBox junctionBox : junctionBoxes) {
                double distance = calculateDistance(junctionBox, newJunctionBox);
                distances.put(Set.of(junctionBox, newJunctionBox), distance);
            }
            junctionBoxes.add(newJunctionBox);
            id++;
        }

        List<Map.Entry<Set<JunctionBox>, Double>> entries = new ArrayList<>(distances.entrySet());
        entries.sort(Map.Entry.comparingByValue());

        Map<Set<JunctionBox>, Double> sortedDistances = new LinkedHashMap<>();
        for (Map.Entry<Set<JunctionBox>, Double> entry : entries) {
            sortedDistances.put(entry.getKey(), entry.getValue());
        }

        Set<Set<JunctionBox>> circuits = new HashSet<>();

        long iter = 0;
        for (Map.Entry<Set<JunctionBox>, Double> entry : sortedDistances.entrySet()) {
            if (iter == NUMBER_OF_CONNECTIONS) {
                break;
            }
            iter++;
            Set<JunctionBox> currentJunctionBoxes = entry.getKey();
            JunctionBox junctionBox1 = currentJunctionBoxes.stream().findFirst().get();
            JunctionBox junctionBox2 = currentJunctionBoxes.stream().filter(junctionBox -> !junctionBox.equals(junctionBox1)).findFirst().get();

            //get set where box 1 is present if it exists
            Optional<Set<JunctionBox>> circuit1 = circuits.stream().filter(circuit -> circuit.contains(junctionBox1)).findFirst();
            //get set where box 2 is present if it exists
            Optional<Set<JunctionBox>> circuit2 = circuits.stream().filter(circuit -> circuit.contains(junctionBox2)).findFirst();

            //if both have sets and are not in the same set merge them into a single set
            if (circuit1.isPresent() && circuit2.isPresent()) {
                if (circuit1.get() == circuit2.get()) {
                    continue;
                }
                Set<JunctionBox> newCircuit1 = new HashSet<>(circuit1.get());
                newCircuit1.addAll(circuit2.get());
                circuits.remove(circuit1.get());
                circuits.remove(circuit2.get());
                circuits.add(newCircuit1);
            }
            //if box 1 has set and 2 doesn't place them in box 1 set
            else if (circuit1.isPresent() && !circuit2.isPresent()) {
                Set<JunctionBox> newCircuit1 = new HashSet<>(circuit1.get());
                newCircuit1.add(junctionBox2);
                circuits.remove(circuit1.get());
                circuits.add(newCircuit1);
            }
            //if box 2 has set and 1 doesn't place them in box 2 set
            else if (!circuit1.isPresent() && circuit2.isPresent()) {
                Set<JunctionBox> newCircuit2 = new HashSet<>(circuit2.get());
                newCircuit2.add(junctionBox1);
                circuits.remove(circuit2.get());
                circuits.add(newCircuit2);
            }
            //if neither have a set place them in a new circuit
            else if (!circuit1.isPresent() && !circuit2.isPresent()){
                Set<JunctionBox> newCircuit = new HashSet<>();
                newCircuit.add(junctionBox1);
                newCircuit.add(junctionBox2);
                circuits.add(newCircuit);
            }
        }

        List<Integer> sizes = circuits.stream().map(Set::size).sorted(Comparator.reverseOrder()).collect(Collectors.toList());

        return sizes.get(0) * sizes.get(1) * sizes.get(2);
    }

    private double calculateDistance(JunctionBox junctionBox, JunctionBox newJunctionBox) {
        return Math.sqrt(Math.pow(junctionBox.getX() - newJunctionBox.getX(), 2) +
                Math.pow(junctionBox.getY() - newJunctionBox.getY(), 2) +
                Math.pow(junctionBox.getZ() - newJunctionBox.getZ(), 2));
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        long result = 0;
        String line;
        long id = 0;
        Set<JunctionBox> junctionBoxes = new HashSet<>();
        Map<Set<JunctionBox>, Double> distances = new HashMap<>();
        while ((line = br.readLine()) != null) {
            String[] coordinates = line.split(",");
            JunctionBox newJunctionBox = new JunctionBox(id,
                    Long.parseLong(coordinates[0]),
                    Long.parseLong(coordinates[1]),
                    Long.parseLong(coordinates[2]));
            for (JunctionBox junctionBox : junctionBoxes) {
                double distance = calculateDistance(junctionBox, newJunctionBox);
                distances.put(Set.of(junctionBox, newJunctionBox), distance);
            }
            junctionBoxes.add(newJunctionBox);
            id++;
        }

        List<Map.Entry<Set<JunctionBox>, Double>> entries = new ArrayList<>(distances.entrySet());
        entries.sort(Map.Entry.comparingByValue());

        Map<Set<JunctionBox>, Double> sortedDistances = new LinkedHashMap<>();
        for (Map.Entry<Set<JunctionBox>, Double> entry : entries) {
            sortedDistances.put(entry.getKey(), entry.getValue());
        }

        Set<Set<JunctionBox>> circuits = new HashSet<>();

        long iter = 0;
        for (Map.Entry<Set<JunctionBox>, Double> entry : sortedDistances.entrySet()) {
            iter++;
            Set<JunctionBox> currentJunctionBoxes = entry.getKey();
            JunctionBox junctionBox1 = currentJunctionBoxes.stream().findFirst().get();
            JunctionBox junctionBox2 = currentJunctionBoxes.stream().filter(junctionBox -> !junctionBox.equals(junctionBox1)).findFirst().get();

            //get set where box 1 is present if it exists
            Optional<Set<JunctionBox>> circuit1 = circuits.stream().filter(circuit -> circuit.contains(junctionBox1)).findFirst();
            //get set where box 2 is present if it exists
            Optional<Set<JunctionBox>> circuit2 = circuits.stream().filter(circuit -> circuit.contains(junctionBox2)).findFirst();

            //if both have sets and are not in the same set merge them into a single set
            if (circuit1.isPresent() && circuit2.isPresent()) {
                if (circuit1.get() == circuit2.get()) {
                    continue;
                }
                Set<JunctionBox> newCircuit1 = new HashSet<>(circuit1.get());
                newCircuit1.addAll(circuit2.get());
                circuits.remove(circuit1.get());
                circuits.remove(circuit2.get());
                circuits.add(newCircuit1);
            }
            //if box 1 has set and 2 doesn't place them in box 1 set
            else if (circuit1.isPresent() && !circuit2.isPresent()) {
                Set<JunctionBox> newCircuit1 = new HashSet<>(circuit1.get());
                newCircuit1.add(junctionBox2);
                circuits.remove(circuit1.get());
                circuits.add(newCircuit1);
            }
            //if box 2 has set and 1 doesn't place them in box 2 set
            else if (!circuit1.isPresent() && circuit2.isPresent()) {
                Set<JunctionBox> newCircuit2 = new HashSet<>(circuit2.get());
                newCircuit2.add(junctionBox1);
                circuits.remove(circuit2.get());
                circuits.add(newCircuit2);
            }
            //if neither have a set place them in a new circuit
            else if (!circuit1.isPresent() && !circuit2.isPresent()){
                Set<JunctionBox> newCircuit = new HashSet<>();
                newCircuit.add(junctionBox1);
                newCircuit.add(junctionBox2);
                circuits.add(newCircuit);
            }

            if (circuits.size() == 1 && circuits.stream().findFirst().get().size() == junctionBoxes.size()) {
                return junctionBox1.getX() * junctionBox2.getX();
            }
        }

        return -1;
    }
}
