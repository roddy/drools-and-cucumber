package app.roddy.space.data.transit;

import app.roddy.space.data.sector.Point;

public class Transit {

    private Point origin = null;
    private Point destination = null;

    private double pilotSkillModifier = 0;
    private double shipBaseRange = -1;

    public double getMaxRange() {
        return shipBaseRange + pilotSkillModifier;
    }

    public double getShipBaseRange() {
        return shipBaseRange;
    }

    public void setShipBaseRange(double shipBaseRange) {
        this.shipBaseRange = shipBaseRange;
    }

    public double getPilotSkillModifier() {
        return pilotSkillModifier;
    }

    public void setPilotSkillModifier(double pilotSkillModifier) {
        this.pilotSkillModifier = pilotSkillModifier;
    }

    public Point getOrigin() {
        return origin;
    }

    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    public Point getDestination() {
        return destination;
    }

    public void setDestination(Point destination) {
        this.destination = destination;
    }

    public boolean isInRange() {
        if(origin == null || destination == null || getMaxRange() <= 0) {
            return false;
        } else {
            return getMaxRange() >= origin.distance(destination);
        }
    }
}
