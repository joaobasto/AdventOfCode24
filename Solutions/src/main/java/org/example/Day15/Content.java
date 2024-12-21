package org.example.Day15;

public enum Content {

    EMPTY("."),
    WALL("#"),
    BOX("O"),
    ROBOT("@"),
    BOX_LEFT_SIDE("["),
    BOX_RIGHT_SIDE("]");

    public final String representation;

    Content(String representation) {
        this.representation = representation;
    }

    @Override
    public String toString() {
        return representation;
    }
}
