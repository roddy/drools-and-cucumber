package app.roddy.space.validation.cargo;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = { "html:target/cucumber_html/cargoValidation/",
                            "json:target/json-cargoValidation.json",
                            "junit:target/junit-cargoValidation.xml"},
                 glue = {"app.roddy.space.validation.cargo",
                         "app.roddy.space.common"},
                 features = "src/test/resources/features/validation/cargoValidation.feature",
                 tags = "@validation")
public class CargoValidationRulesTest {

}
