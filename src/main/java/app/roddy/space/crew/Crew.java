package app.roddy.space.crew;

import app.roddy.space.Person;

import java.util.concurrent.ConcurrentHashMap;

/**
 * The Crew represents the people working on a spaceship. It does not include passengers. Each crew member has an
 * occupation, and there can be at most one crew member with each occupation present.
 */
public class Crew {

    private final ConcurrentHashMap<Occupation, Person> crewByOccupation = new ConcurrentHashMap<>();

    public Person getCrewMemberByOccupation(Occupation occupation) {
        return crewByOccupation.get(occupation);
    }

    public int getCrewSize() {
        return crewByOccupation.size();
    }

    public void addCrewMember(Occupation occupation, Person person) {
        if(person != null) {
            crewByOccupation.put(occupation, person);
        }
    }
}
