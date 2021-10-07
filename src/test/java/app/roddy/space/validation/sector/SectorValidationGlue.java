package app.roddy.space.validation.sector;

import app.roddy.space.Planet;
import app.roddy.space.Sector;
import app.roddy.space.data.Species;
import app.roddy.space.data.planet.CommerceType;
import app.roddy.space.data.planet.Size;
import app.roddy.space.data.sector.Point;
import app.roddy.space.utilities.CucumberUtilities;
import app.roddy.space.utilities.DrlUtilities;
import app.roddy.space.validation.ValidationResult;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Steps and hooks for sector validation features.
 */
public class SectorValidationGlue {

    private static final Logger logger = LoggerFactory.getLogger(SectorValidationGlue.class);

    /**
     * The sector object being tested.  
     */
    private Sector sectorUnderTest;

    /**
     * The result of the validation rules, when the sectorUnderTest is fired against them.
     */
    private ValidationResult result;

    /**
     * Initializes the environment before running the scenario.
     */
    @Before("@validation")
    public void initializeScenario() {
        sectorUnderTest = new Sector();
        result = null;
        isRulesFired.set(false);
    }

    @When("^I have a sector( with designation \".*\")?$")
    public void createSector(String designation) {
        if(designation != null && !designation.isBlank()) {
            designation = designation.substring(designation.indexOf('"'));
            designation = designation.replaceAll("\"", "");
            sectorUnderTest.setDesignation(designation);
        }
    }

    @When("^my sector contains the following planets:$")
    public void setPlanets(DataTable dt) {
        List<Map<String, String>> planetCharacteristics = CucumberUtilities.dataTableToMaps(dt);
        for(Map<String, String> planetData : planetCharacteristics) {
            Planet planet = convertMapToPlanet(planetData);
            sectorUnderTest.addPlanet(planet);
        }
    }

    private Planet convertMapToPlanet(Map<String, String> planetData) {
        Planet planet = new Planet();
        for(Entry<String, String> data : planetData.entrySet()) {
            String key = data.getKey();
            String value = data.getValue();
            switch (key) {
                case "commerce" -> planet.setCommerceType(CommerceType.valueOf(value.toUpperCase()));
                case "species" -> planet.setInhabitantSpecies(Species.valueOf(value.toUpperCase()));
                case "size" -> planet.setSize(Size.valueOf(value.toUpperCase()));
                case "name" -> planet.setName(value);
                case "location" -> {
                    String[] coords = value.split(",");
                    if (coords.length == 3) {
                        Point p = new Point();
                        p.x = Integer.parseInt(coords[0].trim());
                        p.y = Integer.parseInt(coords[1].trim());
                        p.z = Integer.parseInt(coords[2].trim());
                        planet.setLocation(p);
                    }
                }
                default -> logger.warn("Failed to parse planet data with key {} and value {}. Skipping.", key, value);
            }
        }
        return planet;
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
    @Then("^my sector is (not )?valid$")
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
     * Fires the rules using the sectorUnderTest as the input. The rules are only fired if they've not been fired
     * previously (eg we'll only fire once), so it is safe to call this method multiple times.
     * <p>
     * Once the rules are fired, we assert that the result object is actually populated as it should have been.
     *
     * @throws Exception if something goes wrong while firing the rules
     */
    public void fireRules() throws Exception {
        if(isRulesFired.compareAndSet(false, true)) {
            result = new ValidationResult();
            DrlUtilities.fireRulesForScope(SCOPE, sectorUnderTest, "validation", result);
        }
        assertNotNull(result);
    }
}

