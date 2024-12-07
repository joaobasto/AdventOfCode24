package org.example.Day6;

import org.example.utils.Copiable;

public class PositionData implements Copiable<PositionData> {

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

    @Override
    public PositionData createCopy() {
        return new PositionData(isObstacle(), isBelongsToGaurdPath());
    }
}
