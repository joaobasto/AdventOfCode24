package org.example.Day12;

import org.example.AbstractSolver;
import org.example.utils.Grid;
import org.example.utils.Position2D;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day12Solver extends AbstractSolver {
    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        //initial setup of the grid and the guard
        Grid<Node> grid = new Grid<>();
        String line;
        long y = 0;
        while ((line = br.readLine()) != null) {
            char[] characters = line.toCharArray();
            for(long x = 0; x < characters.length; x++) {
                Character currentCharacter = characters[(int) x];
                grid.addElement(x, y, new Node(x, y, currentCharacter));
                //check if node should be connected to left and up node
                if (x > 0 && currentCharacter.equals(grid.getElement(x - 1, y).getCharacter())) {
                    grid.getElement(x, y).addAdjacentNodes(grid.getElement(x - 1, y));
                    grid.getElement(x - 1, y).addAdjacentNodes(grid.getElement(x, y));
                }
                if (y > 0 && currentCharacter.equals(grid.getElement(x, y - 1).getCharacter())) {
                    grid.getElement(x, y).addAdjacentNodes(grid.getElement(x, y - 1));
                    grid.getElement(x, y - 1).addAdjacentNodes(grid.getElement(x, y));
                }
            }
            grid.setNumberOfColumns(characters.length);
            y++;
        }
        grid.setNumberOfRows(y);


        long result = 0;
        for (int i = 0; i < grid.getNumberOfColumns(); i++) {
            for (int j = 0; j < grid.getNumberOfRows(); j++) {
                if (grid.getElement(i, j).wasVisited()) {
                    continue;
                }
                result += getPrice(grid.getElement(i, j));
            }
        }

        return result;
    }

    private long getPrice(Node initialNode) {
        long area = 0;
        long perimeter = 0;
        //perform BFS
        initialNode.setVisited(true);
        Queue<Node> nodesToExplore = new ArrayDeque<>();
        nodesToExplore.add(initialNode);
        while (!nodesToExplore.isEmpty()) {
            Node nextNode = nodesToExplore.remove();
            area++;
            perimeter = perimeter + (4 - nextNode.getAdjacentNodes().size());
            for (Node adjacentNode : nextNode.getAdjacentNodes()) {
                if (adjacentNode.wasVisited()) {
                    continue;
                }
                adjacentNode.setVisited(true);
                nodesToExplore.add(adjacentNode);
            }
        }
        return area * perimeter;
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        //initial setup of the grid and the guard
        Grid<Node> grid = new Grid<>();
        String line;
        long y = 0;
        while ((line = br.readLine()) != null) {
            char[] characters = line.toCharArray();
            for(long x = 0; x < characters.length; x++) {
                Character currentCharacter = characters[(int) x];
                grid.addElement(x, y, new Node(x, y, currentCharacter));
                //check if node should be connected to left and up node
                if (x > 0 && currentCharacter.equals(grid.getElement(x - 1, y).getCharacter())) {
                    grid.getElement(x, y).addAdjacentNodes(grid.getElement(x - 1, y));
                    grid.getElement(x, y).leftNode = grid.getElement(x - 1, y);
                    grid.getElement(x - 1, y).addAdjacentNodes(grid.getElement(x, y));
                    grid.getElement(x - 1, y).rightNode = grid.getElement(x, y);
                }
                if (y > 0 && currentCharacter.equals(grid.getElement(x, y - 1).getCharacter())) {
                    grid.getElement(x, y).addAdjacentNodes(grid.getElement(x, y - 1));
                    grid.getElement(x, y).upNode = grid.getElement(x, y - 1);
                    grid.getElement(x, y - 1).addAdjacentNodes(grid.getElement(x, y));
                    grid.getElement(x, y - 1).downNode = grid.getElement(x, y);
                }
            }
            grid.setNumberOfColumns(characters.length);
            y++;
        }
        grid.setNumberOfRows(y);


        long result = 0;
        for (int i = 0; i < grid.getNumberOfColumns(); i++) {
            for (int j = 0; j < grid.getNumberOfRows(); j++) {
                if (grid.getElement(i, j).wasVisited()) {
                    continue;
                }
                result += getPrice2(grid.getElement(i, j));
            }
        }

        return result;
    }

    private long getPrice2(Node initialNode) {
        long area = 0;
        long perimeter = 0;
        //perform BFS
        initialNode.setVisited(true);
        Queue<Node> nodesToExplore = new ArrayDeque<>();
        List<Node> exploredNodes = new ArrayList<>();
        nodesToExplore.add(initialNode);
        while (!nodesToExplore.isEmpty()) {
            Node nextNode = nodesToExplore.remove();
            area++;


            //we count left side if there is no adjacent node on the left
            //and there isn't an already explored node connected to this one
            //that does not have a node on the left side
            if (nextNode.leftNode == null) {
                boolean condition = nextNode.getAdjacentNodes().stream()
                        .filter(exploredNodes::contains)
                        .anyMatch(node -> node.leftNode == null);
                if (!condition) {
                    perimeter++;
                }
            }
            if (nextNode.rightNode == null) {
                boolean condition = nextNode.getAdjacentNodes().stream()
                        .filter(exploredNodes::contains)
                        .anyMatch(node -> node.rightNode == null);
                if (!condition) {
                    perimeter++;
                }
            }
            if (nextNode.upNode == null) {
                boolean condition = nextNode.getAdjacentNodes().stream()
                        .filter(exploredNodes::contains)
                        .anyMatch(node -> node.upNode == null);
                if (!condition) {
                    perimeter++;
                }
            }
            if (nextNode.downNode == null) {
                boolean condition = nextNode.getAdjacentNodes().stream()
                        .filter(exploredNodes::contains)
                        .anyMatch(node -> node.downNode == null);
                if (!condition) {
                    perimeter++;
                }
            }



            for (Node adjacentNode : nextNode.getAdjacentNodes()) {
                if (adjacentNode.wasVisited()) {
                    continue;
                }
                adjacentNode.setVisited(true);
                nodesToExplore.add(adjacentNode);
            }
            exploredNodes.add(nextNode);
        }
        return area * perimeter;
    }
}
