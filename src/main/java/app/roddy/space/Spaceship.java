package app.roddy.space;

import app.roddy.space.crew.Crew;
import app.roddy.space.crew.Occupation;
import app.roddy.space.data.Classification;
import app.roddy.space.data.ShipType;
import app.roddy.space.data.Species;
import app.roddy.space.data.sector.Point;

public class Spaceship {

    private Species belongsTo = null;
    private String name = null;
    private Classification classification = null;
    private ShipType shipType = null;
    private Crew crew = null;
    private int complement = 1;
    private Point location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Species getBelongsTo() {
        return belongsTo;
    }

    public void setBelongsTo(Species belongsTo) {
        this.belongsTo = belongsTo;
    }

    public Person getCaptain() {
        return getCrewMember(Occupation.CAPTAIN);
    }

    public void setCaptain(Person captain) {
        addCrewMember(Occupation.CAPTAIN, captain);
    }

    public Classification getClassification() {
        return classification;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public int getComplement() {
        return complement;
    }

    public void setComplement(int complement) {
        this.complement = complement;
    }

    public Crew getCrew() {
        return crew;
    }

    public void setCrew(Crew crew) {
        this.crew = crew;
    }

    public void addCrewMember(Occupation occupation, Person person) {
        if(crew == null) {
            crew = new Crew();
        }
        crew.addCrewMember(occupation, person);
    }
    public Person getCrewMember(Occupation occupation) {
        return crew != null ? crew.getCrewMemberByOccupation(occupation) : null;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }
}
