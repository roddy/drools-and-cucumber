package app.roddy.space.validation.cargo;

import app.roddy.space.Cargo;
import app.roddy.space.data.cargo.CargoType;
import app.roddy.space.data.cargo.Dimensions;
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

public class CargoValidationGlue {

    private Cargo cargoUnderTest;
    private ValidationResult result;

    @Before("@validation")
    public void initializeScenario() {
        this.cargoUnderTest = new Cargo();
        this.result = null;
        this.isRulesFired.set(false);
    }

    @When("^I have (.*) cargo$")
    public void setCargoType(String type) {
        cargoUnderTest.setCargoType(CargoType.valueOf(type.toUpperCase().replaceAll("\\s+", "_")));
    }

    @When("^I have (.* )?cargo called \"(.*)\"$")
    public void setCargoTypeAndName(String type, String name) {
        if(type != null && !type.isBlank()) {
            setCargoType(type.trim());
        }
        cargoUnderTest.setName(name);
    }

    @When("^my cargo has length (-?\\d+), width (-?\\d+), and depth (-?\\d+)")
    public void setLengthWidthAndDepth(int length, int width, int depth) {
        Dimensions dimensions = cargoUnderTest.getDimensions();
        if(dimensions == null) {
            dimensions = new Dimensions();
            cargoUnderTest.setDimensions(dimensions);
        }
        dimensions.setLength(length);
        dimensions.setWidth(width);
        dimensions.setDepth(depth);
    }
    
    @When("^my cargo weighs (-?\\d+) pounds")
    public void setWeight(int pounds) {
        Dimensions dimensions = cargoUnderTest.getDimensions();
        if (dimensions == null) {
            dimensions = new Dimensions();
            cargoUnderTest.setDimensions(dimensions);
        }
        dimensions.setLbs(pounds);
    }
    
    @When("^my cargo can be described as \"(.*)\"")
    public void setDescription(String description) {
        cargoUnderTest.setDescription(description);
    }
    
    @When("^my cargo is (il)?legal$")
    public void setLegality(String il) {
        cargoUnderTest.setIsLegal(il == null || il.isBlank());
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
    @Then("^my cargo is (not )?valid$")
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
     * Fires the rules if needed, then asserts that all of the messages are present in the result. The mesages should be
     * separated by a semi-colon.
     *
     * @param messages the list of messages to assert are present
     * @throws Exception if we fail to fire rules due to an Exception
     */
    @Then("^there are error messages: \"(.*)\"$")
    public void assertAllMessages(String messages) throws Exception {
        fireRules();

        String actualMessage = result.getMessage();
        assertNotNull(actualMessage);
        for(String message : messages.split("; ")) {
            assertTrue(actualMessage.contains(message));
        }
    }

    //
    // FRAMEWORK:
    //

    /**
     * The rules scope we are dealing with in this test.
     */
    private static String SCOPE = "validation";

    /**
     * Flag indicating if the rules have been fired. If they've been previously fired, we don't want to fire them again
     * because that may invalidate our test results.
     */
    private AtomicBoolean isRulesFired = new AtomicBoolean(false);

    /**
     * Fires the rules using the cargoUnderTest as the input. The rules are only fired if they've not been fired
     * previously (eg we'll only fire once), so it is safe to call this method multiple times.
     * <p>
     * Once the rules are fired, we assert that the result object is actually populated as it should have been.
     *
     * @throws Exception if something goes wrong while firing the rules
     */
    public void fireRules() throws Exception {
        if(isRulesFired.compareAndSet(false, true)) {
            result = new ValidationResult();
            DrlUtilities.fireRulesForScope(SCOPE, cargoUnderTest, "validation", result);
        }
        assertNotNull(result);
    }
}
