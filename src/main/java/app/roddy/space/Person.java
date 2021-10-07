package app.roddy.space;

import app.roddy.space.crew.Occupation;
import app.roddy.space.data.Gender;
import app.roddy.space.data.Species;
import app.roddy.space.data.person.LatentSkillLevel;
import app.roddy.space.data.person.LearnedSkillLevel;

import java.util.HashMap;
import java.util.Map;

public class Person {

    private String name;
    private Species species;
    private Gender gender;

    private Map<Occupation, LatentSkillLevel> latentSkills = new HashMap<>();
    private Map<Occupation, LearnedSkillLevel> learnedSkills = new HashMap<>();

    public Person() {

    }

    // Copy constructor
    public Person(Person toCopy) {
        this.name = toCopy.name;
        this.species = toCopy.species;
        this.gender = toCopy.gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Map<Occupation, LatentSkillLevel> getLatentSkills() {
        return latentSkills;
    }

    public void setLatentSkills(Map<Occupation, LatentSkillLevel> latentSkills) {
        this.latentSkills = latentSkills;
    }

    public void addLatentSkill(Occupation occupation, LatentSkillLevel skillLevel) {
        latentSkills.put(occupation, skillLevel);
    }

    public void removeLatentSkill(Occupation occupation) {
        latentSkills.remove(occupation);
    }

    public LatentSkillLevel getLatentSkillForOccupation(Occupation occupation){
        return latentSkills.get(occupation);
    }

    public Map<Occupation, LearnedSkillLevel> getLearnedSkills() {
        return learnedSkills;
    }

    public void setLearnedSkills(Map<Occupation, LearnedSkillLevel> learnedSkills) {
        this.learnedSkills = learnedSkills;
    }

    public void addLearnedSkill(Occupation occupation, LearnedSkillLevel skillLevel) {
        learnedSkills.put(occupation, skillLevel);
    }

    public void removeLearnedSkill(Occupation occupation) {
        learnedSkills.remove(occupation);
    }

    public LearnedSkillLevel getLearnedSkillForOccupation(Occupation occupation) {
        return learnedSkills.get(occupation);
    }
}
