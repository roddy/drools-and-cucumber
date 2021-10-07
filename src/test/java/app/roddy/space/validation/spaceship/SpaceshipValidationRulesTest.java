package app.roddy.space.validation.spaceship;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = { "html:target/cucumber_html/spaceshipValidation/",
                            "json:target/json-spaceshipValidation.json",
                            "junit:target/junit-spaceshipValidation.xml"},
                 glue = {"app.roddy.space.validation.spaceship",
                         "app.roddy.space.common"},
                 features = "src/test/resources/features/validation/spaceshipValidation.feature",
                 tags = "@validation")
public class SpaceshipValidationRulesTest {

}
