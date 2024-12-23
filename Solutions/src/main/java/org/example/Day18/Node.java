package org.example.Day18;

import org.example.utils.Direction2D;
import org.example.utils.Position2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {

    Position2D position2D;

    List<Node> adjacentNodes = new ArrayList<>();

    Long distance;

    boolean visited = false;

    public Node(long x, long y) {
        this.position2D = new Position2D(x, y);
    }

    public Position2D getPosition2D() {
        return position2D;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    public boolean wasVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public List<Node> getAdjacentNodes() {
        return adjacentNodes;
    }

    public void addAdjacentNodes(Node adjacentNode) {
        this.adjacentNodes.add(adjacentNode);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(position2D, node.position2D);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position2D);
    }
}
