package org.example.Day24;

import java.util.*;

import static org.example.Day24.Operation.*;

public class Node {
    String name;
    boolean isWire;
    Boolean output;

    Operation operation;
    List<Node> childNodes = new ArrayList<>();

    public boolean getValue() {
        if (isWire) {
            return output;
        }
        if (output != null) {
            return output;
        }
        if (operation == AND) {
            output = childNodes.get(0).getValue() && childNodes.get(1).getValue();
        }
        if (operation == OR) {
            output = childNodes.get(0).getValue() || childNodes.get(1).getValue();
        }
        if (operation == XOR) {
            output = childNodes.get(0).getValue() ^ childNodes.get(1).getValue();
        }
        return output;
    }

    public Node(String name, boolean isWire) {
        this.name = name;
        this.isWire = isWire;
    }

    public Node(String name, boolean isWire, boolean output) {
        this.name = name;
        this.isWire = isWire;
        this.output = output;
    }

    public Node(String name, boolean isWire, Operation operation) {
        this.name = name;
        this.isWire = isWire;
        this.operation = operation;
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
}
