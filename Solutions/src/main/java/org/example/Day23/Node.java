package org.example.Day23;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Node {
    String name;

    Set<Node> adjacentNodes = new HashSet<>();

    public Node(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(name, node.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
