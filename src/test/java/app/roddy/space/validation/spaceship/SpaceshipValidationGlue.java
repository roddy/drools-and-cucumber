package app.roddy.space.validation.spaceship;

import app.roddy.space.Person;
import app.roddy.space.Spaceship;
import app.roddy.space.crew.Occupation;
import app.roddy.space.data.Classification;
import app.roddy.space.data.Gender;
import app.roddy.space.data.ShipType;
import app.roddy.space.data.Species;
import app.roddy.space.data.sector.Point;
import app.roddy.space.utilities.DrlUtilities;
import app.roddy.space.validation.ValidationResult;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * The glue (steps and hooks) for the spaceship validation test.
 */
public class SpaceshipValidationGlue {

    /**
     * The spaceship that will be validated.
     */
    private Spaceship spaceshipUnderTest;

    /**
     * The results of the validation.
     */
    private ValidationResult result;

    /**
     * Initializes the objects under test. Creates a new Spaceship and removes any previously existing validation results.
     */
    @Before("@validation")
    public void initializeScenario() {
        spaceshipUnderTest = new Spaceship();
        result = null;

        isRulesFired.set(false);
    }

    //
    // INITIALIZATION STEPS:
    //

    /**
     * Sets the spaceship's name and owning species.
     * @param species the species
     * @param name the name
     */
    @When("^I have an? (Human|Alien|) spaceship named \"(.*)\"$")
    public void setSpaceshipSpeciesAndName(String species, String name) {
        setSpaceshipSpecies(species);
        setSpaceshipName(name);
    }

    /**
     * Sets the spaceship name.
     * @param name the name
     */
    @When("^I have a spaceship named \"(.*)\"$")
    public void setSpaceshipName(String name) {
        if(name != null && name.isBlank()) {
            name = null;
        }
        spaceshipUnderTest.setName(name);
    }

    /**
     * Sets the spaceship's owning species.
     * @param species the species
     */
    @When("^I have an? (Human|Alien|) spaceship$")
    public void setSpaceshipSpecies(String species) {
        if(species.isBlank()) {
            spaceshipUnderTest.setBelongsTo(null);
        } else {
            spaceshipUnderTest.setBelongsTo(Species.valueOf(species.toUpperCase()));
        }
    }

    /**
     * Sets the spaceship's classification
     * @param classification the classification
     */
    @When("^my spaceship is an? (Civilian|Military|Pirate|) ship$")
    public void setSpaceshipClassification(String classification) {
        if(classification.isBlank()) {
            spaceshipUnderTest.setClassification(null);
        } else {
            spaceshipUnderTest.setClassification(Classification.valueOf(classification.toUpperCase()));
        }
    }

    /**
     * Sets the spaceship's classification and type
     * @param classification the classification
     * @param type the type
     */
    @When("^my spaceship is an? (Military|Civilian|Pirate|) ship of type (Freighter|Transport|Corvette|Dreadnaught|)$")
    public void setSpaceshipClassification(String classification, String type) {
        setSpaceshipClassification(classification);

        if(type.isBlank()) {
            spaceshipUnderTest.setShipType(null);
        } else {
            spaceshipUnderTest.setShipType(ShipType.valueOf(type.toUpperCase()));
        }
    }

    /**
     * Creates and adds a crew member to the spaceship, with the given occupation, species, gender, and name.
     * @param occupation the crew member occupation
     * @param species the crew member's species
     * @param gender the crew member's gender
     * @param name the crew member's name
     */
    @When("^the (.+) is an? (Alien|Human|) ([mM]ale|[Ff]emale|) named \"(.*)\"$")
    public void addCrewMember(String occupation, String species, String gender, String name) {
        if(species.isBlank() && gender.isBlank() && name.isBlank()) {
            spaceshipUnderTest.setCaptain(null);
        } else {
            occupation = occupation.toUpperCase().replaceAll("\\s+", "_");

            Person crewMember = spaceshipUnderTest.getCrewMember(Occupation.valueOf(occupation));
            if (crewMember == null) {
                crewMember = new Person();
                spaceshipUnderTest.addCrewMember(Occupation.valueOf(occupation), crewMember);
            }
            crewMember.setSpecies(Species.valueOf(species.toUpperCase()));
            crewMember.setGender(Gender.valueOf(gender.toUpperCase()));
            crewMember.setName(name);
        }
    }

    /**
     * Sets the crew complement (aka: the maximum size of the crew.)
     * @param complement the complement
     */
    @When("^my crew complement is (-?\\d+)$")
    public void setCrewComplement(int complement) {
        spaceshipUnderTest.setComplement(complement);
    }

    /**
     * Removes any existing name from the spaceship.
     */
    @When("^my spaceship has no name$")
    public void removeSpaceshipName() {
        setSpaceshipName(null);
    }

    @When("^my spaceship is located at \\((-?\\d+), ?(-?\\d+), ?(-?\\d+)\\)$")
    public void setPosition(int x, int y, int z) {
        Point p = new Point();
        p.x = x;
        p.y = y;
        p.z = z;
        spaceshipUnderTest.setLocation(p);
    }

    //
    // ASSERTIONS
    //

    /**
     * Fires the rules if needed and asserts against the validity of the response.
     *
     * @param not if set, then we assert at the input was not valid; otherwise we assert that it was valid
     * @throws Exception if we fail to fire rules due to an Exception
     */
    @Then("^my spaceship is (not )?valid$")
    public void assertValidity(String not) throws Exception {
        fireRules();
        if(not == null || not.trim().isEmpty()) {
            assertTrue(result.isValid());
        } else {
            assertFalse(result.isValid());
        }
    }

    /**
     * Fires the rules if needed and asserts that the error message is what is expected
     *
     * @param message the expected error message
     * @throws Exception if we fail to fire rules due to an Exception
     */
    @Then("^the error message is:? \"(.*)\"$")
    public void assertErrorMessageEquals(String message) throws Exception {
        fireRules();
        assertEquals(message, result.getMessage());
    }

    /**
     * Fires the rules if needed and asserts that there is no error message in the result.
     *
     * @throws Exception if we fail to fire rules due to an Exception
     */
    @Then("^there is no error message")
    public void assertNoErrorMessage() throws Exception {
        fireRules();
        assertNull(result.getMessage());
    }

    @Then("^the error message contains: \"(.*)\"$")
    public void assertErrorMessageContains(String messages) throws Exception {
        fireRules();
        assertNotNull(result.getMessage());
        for(String message : messages.split("; ")) {
            assertTrue(result.getMessage().contains(message));
        }
    }

    //
    // FRAMEWORK:
    //

    /**
     * The rules scope we are dealing with in this test.
     */
    private static final String SCOPE = "validation";

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
            result = new ValidationResult();
            DrlUtilities.fireRulesForScope(SCOPE, spaceshipUnderTest, "validation", result);
        }
        assertNotNull(result);
    }
}
