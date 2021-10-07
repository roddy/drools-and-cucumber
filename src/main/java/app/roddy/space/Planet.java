package app.roddy.space;

import app.roddy.space.data.Species;
import app.roddy.space.data.planet.CommerceType;
import app.roddy.space.data.planet.Size;
import app.roddy.space.data.sector.Point;

public class Planet {
    private String name;
    private Species inhabitantSpecies;
    private Size size;
    private CommerceType commerceType;

    private Point location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Species getInhabitantSpecies() {
        return inhabitantSpecies;
    }

    public void setInhabitantSpecies(Species inhabitantSpecies) {
        this.inhabitantSpecies = inhabitantSpecies;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public CommerceType getCommerceType() {
        return commerceType;
    }

    public void setCommerceType(CommerceType commerceType) {
        this.commerceType = commerceType;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }
}
