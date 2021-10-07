package app.roddy.space.validation.person;

import app.roddy.space.Person;
import app.roddy.space.data.Gender;
import app.roddy.space.data.Species;
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

public class PersonValidationGlue {

    private Person personUnderTest;
    private ValidationResult result;

    @Before("@validation")
    public void initializeScenario() {
        personUnderTest = new Person();
        result = null;
        isRulesFired.set(false);
    }

    @When("^my person is a (male|female) (Human|Alien)$")
    public void setGenderAndSpecies(String gender, String species) {
        setGender(gender);
        setSpecies(species);
    }

    @When("^my person is a (male|female) (Human|Alien) named \"(.*)\"$")
    public void setGenderAndSpeciesAndName(String gender, String species, String name) {
        setGender(gender);
        setSpecies(species);
        setName(name);
    }

    @When("^my person is an? (Alien|Human|)$")
    public void setSpecies(String species) {
        if(species == null || species.trim().isEmpty()) {
            personUnderTest.setSpecies(null);
        } else {
            personUnderTest.setSpecies(Species.valueOf(species.toUpperCase()));
        }
    }

    @When("^my person is named \"(.*)\"$")
    public void setName(String name) {
        if(name == null || name.trim().isEmpty()) {
            personUnderTest.setName(null);
        } else {
            personUnderTest.setName(name);
        }
    }

    @When("^my person is (male|female|)$")
    public void setGender(String gender) {
        if(gender == null || gender.trim().isEmpty()) {
            personUnderTest.setGender(null);
        } else {
            personUnderTest.setGender(Gender.valueOf(gender.toUpperCase()));
        }
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
    @Then("^my person is (not )?valid$")
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
     * @param allMessages the list of messages to assert are present (semi-colon-separated)
     * @throws Exception if we fail to fire rules due to an Exception
     */
    @Then("^there are error messages: \"(.*)\"$")
    public void assertAllMessages(String allMessages) throws Exception {
        fireRules();

        String actualMessage = result.getMessage();
        assertNotNull(actualMessage);
        for(String message : allMessages.split("; ")) {
            assertTrue(actualMessage.contains(message));
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
     * Fires the rules using the personUnderTest as the input. The rules are only fired if they've not been fired
     * previously (eg we'll only fire once), so it is safe to call this method multiple times.
     * <p>
     * Once the rules are fired, we assert that the result object is actually populated as it should have been.
     *
     * @throws Exception if something goes wrong while firing the rules
     */
    public void fireRules() throws Exception {
        if(isRulesFired.compareAndSet(false, true)) {
            result = new ValidationResult();
            DrlUtilities.fireRulesForScope(SCOPE, personUnderTest, "validation", result);
        }
        assertNotNull(result);
    }
}
