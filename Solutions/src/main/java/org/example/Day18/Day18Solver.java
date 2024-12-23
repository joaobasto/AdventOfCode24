package org.example.Day18;

import org.example.AbstractSolver;
import org.example.Day18.Node;
import org.example.utils.Grid;
import org.example.utils.Position2D;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day18Solver extends AbstractSolver {

    private static final int GRID_SIZE  = 71;
    private static final long MEMORY_SIZE = 1024;
    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        //initial setup of the grid and the guard
        Grid<PositionData> grid = new Grid<>();
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid.addElement(i, j, new PositionData(false));
            }
        }
        grid.setNumberOfColumns(GRID_SIZE);
        grid.setNumberOfRows(GRID_SIZE);
        String line;
        long memoryCounter = 0;
        while ((line = br.readLine()) != null) {
            if (memoryCounter == MEMORY_SIZE) {
                break;
            }
            String[] valuesString = line.split(",");
            List<Integer> values = Arrays.stream(valuesString)
                    .map(Integer::valueOf).collect(Collectors.toList());
            grid.getElement(values.get(0), values.get(1)).setCorrupted(true);
            memoryCounter++;
        }

        Node startNode = null;
        Node endNode = null;
        Map<Node, Node> nodes = new HashMap<>();
        Map<Node, Map<Node, Long>> distances = new HashMap<>();
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                Node newNode = new Node(i, j);
                nodes.put(newNode, newNode);
                distances.put(newNode, new HashMap<>());
                //check if node should be connected to left and up node
                Node leftNode = nodes.get(new Node(i - 1, j));
                if (i > 0 && leftNode != null && !grid.getElement(i - 1, j).isCorrupted()
                && !grid.getElement(i, j).isCorrupted()) {
                    leftNode.addAdjacentNodes(newNode);
                    newNode.addAdjacentNodes(leftNode);
                    distances.get(newNode).put(leftNode, 1L);
                    distances.get(leftNode).put(newNode, 1L);
                }
                Node upNode = nodes.get(new Node(i, j - 1));
                if (j > 0 && upNode != null && !grid.getElement(i, j - 1).isCorrupted()
                && !grid.getElement(i, j).isCorrupted()) {
                    upNode.addAdjacentNodes(newNode);
                    newNode.addAdjacentNodes(upNode);
                    distances.get(newNode).put(upNode, 1L);
                    distances.get(upNode).put(newNode, 1L);
                }
                if (i == 0 && j == 0) {
                    startNode = newNode;
                }
                if (i == GRID_SIZE - 1 && j == GRID_SIZE - 1) {
                    endNode = newNode;
                }
            }
        }

        //Dijkstra
        Map<Node, Long> nodeDistances = new HashMap<>();
        for (Node node : nodes.values()) {
            node.setDistance(Long.MAX_VALUE);
        }
        startNode.setDistance( 0L);
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(node -> Math.toIntExact(node.getDistance())));
        pq.add(startNode);
        while (!pq.isEmpty()) {
            Node currentNode = pq.poll();
            for(Node adjacentNode : currentNode.getAdjacentNodes()) {
                long newDistance = currentNode.getDistance() + distances.get(currentNode).get(adjacentNode);
                if (newDistance < adjacentNode.getDistance()) {
                    adjacentNode.setDistance(newDistance);
                    pq.add(adjacentNode);
                }
            }
        }

        return endNode.getDistance();
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        return 0;
    }
}
