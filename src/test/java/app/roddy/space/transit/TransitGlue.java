package app.roddy.space.transit;

import app.roddy.space.Person;
import app.roddy.space.Planet;
import app.roddy.space.Spaceship;
import app.roddy.space.crew.Occupation;
import app.roddy.space.data.Classification;
import app.roddy.space.data.ShipType;
import app.roddy.space.data.person.LatentSkillLevel;
import app.roddy.space.data.person.LearnedSkillLevel;
import app.roddy.space.data.sector.Point;
import app.roddy.space.data.transit.Transit;
import app.roddy.space.utilities.CucumberUtilities;
import app.roddy.space.utilities.DrlUtilities;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

public class TransitGlue {
    private static final Logger logger = LoggerFactory.getLogger(TransitGlue.class);

    private Spaceship spaceshipUnderTest;
    private Planet planetUnderTest;
    private Transit result;

    @Before("@transit")
    public void initializeScenario() {
        this.spaceshipUnderTest = null;
        this.planetUnderTest = null;
        this.result = null;

        this.isRulesFired.set(false);
    }

    @When("^there is a spaceship:$")
    public void setSpaceship(DataTable dt) {
        spaceshipUnderTest = new Spaceship();
        Map<String, String> data = CucumberUtilities.dataTableToMap(dt);
        for(Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key) {
                case "location" -> {
                    String[] parts = value.split(",");
                    if (parts.length == 3) {
                        Point p = new Point();
                        p.x = Integer.parseInt(parts[0].trim());
                        p.y = Integer.parseInt(parts[1].trim());
                        p.z = Integer.parseInt(parts[2].trim());
                        spaceshipUnderTest.setLocation(p);
                    }
                }
                case "classification" -> spaceshipUnderTest.setClassification(Classification.valueOf(value.toUpperCase()));
                case "type" -> spaceshipUnderTest.setShipType(ShipType.valueOf(value.toUpperCase()));
                default -> logger.warn("Unrecognized key {} with value {}. Skipping.", key, value);
            }
        }
    }

    @When("^there is a spaceship at \\((-?\\d+), ?(-?\\d+), ?(-?\\d+)\\)$")
    public void setSpaceshipPosition(int x, int y, int z) {
        if(spaceshipUnderTest == null) {
            spaceshipUnderTest = new Spaceship();
        }
        Point p = new Point();
        p.x = x;
        p.y = y;
        p.z = z;
        spaceshipUnderTest.setLocation(p);
    }

    @When("^the spaceship is a (.*) (.*)")
    public void setSpaceshipClassAndType(String classification, String type) {
        if(spaceshipUnderTest == null) {
            spaceshipUnderTest = new Spaceship();
        }
        spaceshipUnderTest.setClassification(Classification.valueOf(classification.toUpperCase()));
        spaceshipUnderTest.setShipType(ShipType.valueOf(type.toUpperCase()));
    }

    @When("^there is a planet at \\((-?\\d+), ?(-?\\d+), ?(-?\\d+)\\)$")
    public void setPlanet(int x, int y, int z) {
        planetUnderTest = new Planet();
        Point p = new Point();
        p.x = x;
        p.y = y;
        p.z = z;
        planetUnderTest.setLocation(p);
    }

    @When("^there is a planet$")
    public void createPlanet() {
        planetUnderTest = new Planet();
    }

    @When("^the pilot has been trained to the (.*) level$")
    public void setLearnedSkillLevel(String level) {
        if(spaceshipUnderTest == null) {
            spaceshipUnderTest = new Spaceship();
        }

        Person pilot = spaceshipUnderTest.getCrewMember(Occupation.PILOT);
        if(pilot == null) {
            pilot = new Person();
            spaceshipUnderTest.addCrewMember(Occupation.PILOT, pilot);
        }
        pilot.addLearnedSkill(Occupation.PILOT, LearnedSkillLevel.valueOf(level.toUpperCase().replaceAll("\\s+", "_")));
    }

    @When("^the pilot has (.*) latent skills$")
    public void setLatentSkillLevel(String level) {
        if(spaceshipUnderTest == null) {
            spaceshipUnderTest = new Spaceship();
        }

        Person pilot = spaceshipUnderTest.getCrewMember(Occupation.PILOT);
        if(pilot == null) {
            pilot = new Person();
            spaceshipUnderTest.addCrewMember(Occupation.PILOT, pilot);
        }
        pilot.addLatentSkill(Occupation.PILOT, LatentSkillLevel.valueOf(level.toUpperCase().replaceAll("\\s+", "_")));
    }

    @Then("^the trip is (in|out of) range$")
    public void fireRulesAndAssertResult(String inOrOut) throws Exception {
        fireRules();

        if("in".equals(inOrOut)) {
            assertTrue("should be in range", result.isInRange());
        } else if ("out of".equals(inOrOut)) {
            assertFalse(result.isInRange());
        }
    }

    @Then("^the pilot modifier should be (-?[\\d\\.]*\\d+)$")
    public void assertPilotModifier(double value) throws Exception {
        fireRules();

        assertEquals(result.getPilotSkillModifier(), value, 0.0);
    }

    @Then("^the base range should be ([\\d\\.]*\\d+)$")
    public void assertBaseRange(double value) throws Exception {
        fireRules();

        assertEquals(value, result.getShipBaseRange(), 0.0);
    }

    @Then("^the max range should be ([\\d\\.]*\\d+)$")
    public void assertMaxRange(double value) throws Exception {
        fireRules();

        assertEquals(value, result.getMaxRange(), 0.0);
    }

    //
    // FRAMEWORK:
    //

    /**
     * The rules scope we are dealing with in this test.
     */
    private static final String SCOPE = "travel";

    /**
     * Flag indicating if the rules have been fired. If they've been previously fired, we don't want to fire them again
     * because that may invalidate our test results.
     */
    private final AtomicBoolean isRulesFired = new AtomicBoolean(false);

    /**
     * Fires the rules using the spaceshipUnderTest as the input. The rules are only fired if they've not been fired
     * previously (eg we'll only fire once), so it is safe to call this method multiple times.
     * <p>
     * Once the rules are fired, we assert that the result object is actually populated as it should have been.
     *
     * @throws Exception if something goes wrong while firing the rules
     */
    public void fireRules() throws Exception {
        if(isRulesFired.compareAndSet(false, true)) {
            result = new Transit();
            List<Object> ruleInputs = new ArrayList<>();
            ruleInputs.add(result);
            if(spaceshipUnderTest != null) {
                ruleInputs.add(spaceshipUnderTest);
            }
            if(planetUnderTest != null) {
                ruleInputs.add(planetUnderTest);
            }
            DrlUtilities.fireRulesForScope(SCOPE, ruleInputs);
        }
        assertNotNull(result);
    }
}
