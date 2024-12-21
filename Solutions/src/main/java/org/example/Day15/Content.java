package org.example.Day15;

public enum Content {

    EMPTY("."),
    WALL("#"),
    BOX("O"),
    ROBOT("@");

    public final String representation;

    Content(String representation) {
        this.representation = representation;
    }

    @Override
    public String toString() {
        return representation;
    }
}
