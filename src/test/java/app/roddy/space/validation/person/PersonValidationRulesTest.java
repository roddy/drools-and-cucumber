package app.roddy.space.validation.person;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = { "html:target/cucumber_html/personValidation/",
                            "json:target/json-personValidation.json",
                            "junit:target/junit-personValidation.xml"},
                 glue = {"app.roddy.space.validation.person",
                         "app.roddy.space.common"},
                 features = "src/test/resources/features/validation/personValidation.feature",
                 tags = "@validation")
public class PersonValidationRulesTest {
}
