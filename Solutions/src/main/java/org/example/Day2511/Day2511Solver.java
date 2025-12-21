package org.example.Day2511;

import org.example.AbstractSolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day2511Solver extends AbstractSolver {

    private static final String INITIAL_NODE_ID = "svr";

    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        long result = 0;
        String line;
        Map<String, Node> nodesById = new HashMap<>();
        while ((line = br.readLine()) != null) {
            String[] auxString = line.split(": ");
            String currentNodeId = auxString[0];
            String[] adjacentNodesIds = auxString[1].split(" ");
            Node currentNode = nodesById.getOrDefault(currentNodeId, new Node(currentNodeId));
            for (String adjacentNodeId : adjacentNodesIds) {
                Node adjacentNode = nodesById.getOrDefault(adjacentNodeId, new Node(adjacentNodeId));
                currentNode.getAdjacentNodes().add(adjacentNode);
                nodesById.put(adjacentNodeId, adjacentNode);
            }
            nodesById.put(currentNodeId, currentNode);
        }

        Node startNode = nodesById.get(INITIAL_NODE_ID);
        List<List<Node>> solutions = new ArrayList<>();

        //solve(nodesById, youNode, solutions, List.of(youNode));
        Map<Node, Set<Node>> graph = new HashMap<>();
        nodesById.values().stream().forEach(value -> graph.put(value, value.getAdjacentNodes()));


        return allPaths(graph, startNode, nodesById.get("out")).size();
    }

    private void solve(Map<String, Node> nodesById, Node currentNode,
                       List<List<Node>> solutions, List<Node> currentSolution) {
        if (currentNode.getId().equals("out")) {
            solutions.add(currentSolution);
            return;
        }

        for (Node newNode : currentNode.getAdjacentNodes()) {
            if (currentSolution.contains(newNode)) {
                continue;
            }
            List<Node> solution = new ArrayList<>(currentSolution);
            solution.add(newNode);
            solve(nodesById, newNode, solutions, solution);
        }
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        long result = 0;
        String line;
        Map<String, Node> nodesById = new HashMap<>();
        while ((line = br.readLine()) != null) {
            String[] auxString = line.split(": ");
            String currentNodeId = auxString[0];
            String[] adjacentNodesIds = auxString[1].split(" ");
            Node currentNode = nodesById.getOrDefault(currentNodeId, new Node(currentNodeId));
            for (String adjacentNodeId : adjacentNodesIds) {
                Node adjacentNode = nodesById.getOrDefault(adjacentNodeId, new Node(adjacentNodeId));
                currentNode.getAdjacentNodes().add(adjacentNode);
                nodesById.put(adjacentNodeId, adjacentNode);
            }
            nodesById.put(currentNodeId, currentNode);
        }

        Node startNode = nodesById.get(INITIAL_NODE_ID);
        List<List<Node>> solutions = new ArrayList<>();

        //solve(nodesById, youNode, solutions, List.of(youNode));
        Map<Node, Set<Node>> graph = new HashMap<>();
        nodesById.values().stream().forEach(value -> graph.put(value, value.getAdjacentNodes()));


        return allPaths(graph, startNode, nodesById.get("out")).size();
    }

    private boolean containsDesiredNodes(List<Node> solution) {
        return solution.contains(new Node("dac")) && solution.contains(new Node("fft"));
    }

    public static List<List<Node>> allPaths(
            Map<Node, Set<Node>> graph,
            Node start,
            Node end
    ) {
        List<List<Node>> result = new ArrayList<>();
        Deque<Node> path = new ArrayDeque<>();
        dfs(graph, start, end, path, result);
        return result;
    }

    private static void dfs(
            Map<Node, Set<Node>> graph,
            Node current,
            Node end,
            Deque<Node> path,
            List<List<Node>> result
    ) {
        path.addLast(current);

        if (current == end) {
            result.add(new ArrayList<>(path));
        } else {
            for (Node next : graph.getOrDefault(current, Collections.emptySet())) {
                dfs(graph, next, end, path, result);
            }
        }

        path.removeLast(); // backtrack
    }
}
