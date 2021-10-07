package app.roddy.space.validation.planet;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = { "html:target/cucumber_html/planetValidation/",
                            "json:target/json-planetValidation.json",
                            "junit:target/junit-planetValidation.xml"},
                 glue = {"app.roddy.space.validation.planet",
                         "app.roddy.space.common"},
                 features = "src/test/resources/features/validation/planetValidation.feature",
                 tags = "@validation")
public class PlanetValidationRulesTest {
}
