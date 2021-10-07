package app.roddy.space.validation.planet;

import app.roddy.space.Planet;
import app.roddy.space.data.Species;
import app.roddy.space.data.planet.CommerceType;
import app.roddy.space.data.planet.Size;
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
 * Steps and hooks for planet validation features.
 */
public class PlanetValidationGlue {

    /**
     * The planet object being tested.
     */
    private Planet planetUnderTest;

    /**
     * The result of the validation rules, when the planetUnderTest is fired against them.
     */
    private ValidationResult result;

    /**
     * Initializes the environment before running the scenario.
     */
    @Before("@validation")
    public void initializeScenario() {
        planetUnderTest = new Planet();
        result = null;
        isRulesFired.set(false);
    }

    @When("^my planet is located at \\((-?\\d+), ?(-?\\d+), ?(-?\\d+)\\)$")
    public void setCoordinates(int x, int y, int z) {
        Point p = new Point();
        p.x = x;
        p.y = y;
        p.z = z;
        planetUnderTest.setLocation(p);
    }

    /**
     * Sets the planet size
     * @param size the size
     */
    @When("^I have a (tiny|small|medium|large|gigantic) planet$")
    public void setPlanetSize(String size) {
        planetUnderTest.setSize(Size.valueOf(size.toUpperCase()));
    }

    /**
     * Sets the planet size and inhabitant species
     * @param size the size
     * @param name the inhabitant species
     */
    @When("^I have a (tiny|small|medium|large|gigantic) planet named \"(.*)\"$")
    public void setPlanetSizeAndName(String size, String name) {
        setPlanetSize(size);
        setPlanetName(name);
    }

    /**
     * Sets the species of the inhabitants.
     * @param species the species
     */
    @When("^my planet belongs to the (Alien|Human)s$")
    public void setInhabitantSpecies(String species) {
        planetUnderTest.setInhabitantSpecies(Species.valueOf(species.toUpperCase()));
    }

    /**
     * Sets the planet's name
     * @param name the name
     */
    @When("^my planet is named \"(.*)\"$")
    public void setPlanetName(String name) {
        planetUnderTest.setName(name);
    }

    /**
     * Sets the planet's commerce
     * @param commerce the commerce
     */
    @When("^my planet's commerce is primarily (military|agricultural|industrial|commercial)$")
    public void setCommerce(String commerce) {
        planetUnderTest.setCommerceType(CommerceType.valueOf(commerce.toUpperCase()));
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
    @Then("^my planet is (not )?valid$")
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

    /**
     * Fires the rules if needed and asserts that the specified error messages (semi-colon separated) are present in the
     * result.
     * @param messages the messages to assert are present
     * @throws Exception if we fail to fire rues due to an Exception
     */
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
     * Fires the rules using the planetUnderTest as the input. The rules are only fired if they've not been fired
     * previously (eg we'll only fire once), so it is safe to call this method multiple times.
     * <p>
     * Once the rules are fired, we assert that the result object is actually populated as it should have been.
     *
     * @throws Exception if something goes wrong while firing the rules
     */
    public void fireRules() throws Exception {
        if(isRulesFired.compareAndSet(false, true)) {
            result = new ValidationResult();
            DrlUtilities.fireRulesForScope(SCOPE, planetUnderTest, "validation", result);
        }
        assertNotNull(result);
    }
}
