package app.roddy.space.transit;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = { "html:target/cucumber_html/transit/",
                            "json:target/json-transit.json",
                            "junit:target/junit-transit.xml"},
                 glue = {"app.roddy.space.transit"},
                 features = "src/test/resources/features/transit/transitCalculation.feature",
                 tags = "@transit")
public class TransitRulesTest {
}
