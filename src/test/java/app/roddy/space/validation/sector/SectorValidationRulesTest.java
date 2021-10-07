package app.roddy.space.validation.sector;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = { "html:target/cucumber_html/sectorValidation/",
                            "json:target/json-sectorValidation.json",
                            "junit:target/junit-sectorValidation.xml"},
                 glue = {"app.roddy.space.validation.sector",
                         "app.roddy.space.common"},
                 features = "src/test/resources/features/validation/sectorValidation.feature",
                 tags = "@validation")
public class SectorValidationRulesTest {

}
