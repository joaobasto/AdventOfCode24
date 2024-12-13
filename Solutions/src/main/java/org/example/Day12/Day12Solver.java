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
        return 0;
    }
}
