package app.roddy.space;

import app.roddy.space.data.sector.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A sector models a region in space. A sector has a name (designation), and a number of planets in it. Each planet
 * has specific coordinates. The planet itself knows its coordinates because those aren't going to be changing any time
 * soon.
 */
public class Sector {

    private String designation;
    private final List<Planet> planets = new ArrayList<>();
    private final List<Point> bounds = new ArrayList<>();

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public List<Planet> getPlanets() {
        return Collections.unmodifiableList(planets);
    }

    public void addPlanet(Planet p) {
        planets.add(p);
    }

    public void removePlanet(Planet p) {
        planets.remove(p);
    }

    public List<Point> getBounds() {
        return bounds;
    }

    public void setBounds(List<Point> bounds) {
        this.bounds.clear();
        this.bounds.addAll(bounds);
    }

    public void addBound(Point bound) {
        bounds.add(bound);
    }

    public void removeBound(Point bound) {
        bounds.remove(bound);
    }
}
