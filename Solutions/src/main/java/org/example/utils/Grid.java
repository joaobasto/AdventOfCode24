package org.example.utils;

import org.example.Day6.PositionData;

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

    public void addElement(long x, long y, T positionData) {
        Position2D position = new Position2D(x, y);
        map.put(position, positionData);
    }

    public boolean isPositionInGrid(long x, long y) {
        return x >= 0 && x < numberOfColumns && y >= 0 && y < numberOfRows;
    }
}
