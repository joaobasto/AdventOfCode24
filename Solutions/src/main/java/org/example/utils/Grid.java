package org.example.utils;


import java.util.HashMap;
import java.util.Map;

public class Grid<T> {

    private final Map<Position2D, T> map = new HashMap<>();;
    private long numberOfColumns;
    private long numberOfRows;

    public long getNumberOfColumns() {
        return numberOfColumns;
    }

    public void setNumberOfColumns(long numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    public long getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(long numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public Map<Position2D, T> getMap() {
        return map;
    }

    public T getElement(long x, long y) {
        Position2D position = new Position2D(x, y);
        return map.get(position);
    }

    public T getElement(Position2D position) {
        return map.get(position);
    }

    public void setElement(Position2D position, T positionData) {
        map.put(position, positionData);
    }

    public void addElement(long x, long y, T positionData) {
        Position2D position = new Position2D(x, y);
        map.put(position, positionData);
    }

    public boolean isPositionInGrid(long x, long y) {
        return x >= 0 && x < numberOfColumns && y >= 0 && y < numberOfRows;
    }

    public void printGrid() {
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                System.out.print(this.getElement(j, i));
            }
            System.out.println();
        }
        System.out.println();
    }
}
