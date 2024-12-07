package org.example.Day6;

public class PositionData {

    private boolean obstacle;

    private boolean belongsToGaurdPath;

    public PositionData(boolean obstacle, boolean belongsToGaurdPath) {
        this.obstacle = obstacle;
        this.belongsToGaurdPath = belongsToGaurdPath;
    }

    public boolean isObstacle() {
        return obstacle;
    }

    public void setObstacle(boolean obstacle) {
        this.obstacle = obstacle;
    }

    public boolean isBelongsToGaurdPath() {
        return belongsToGaurdPath;
    }

    public void setBelongsToGaurdPath(boolean belongsToGaurdPath) {
        this.belongsToGaurdPath = belongsToGaurdPath;
    }
}
