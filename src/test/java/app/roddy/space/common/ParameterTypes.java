package app.roddy.space.common;

import io.cucumber.java.ParameterType;

import java.util.Arrays;
import java.util.List;

// TODO: https://stackoverflow.com/questions/69474687/cucumber-java-wont-use-custom-parametertype
public class ParameterTypes {
    @ParameterType("(?:.+;)+.+")
    public List<String> stringList(String raw) {
        String[] values = raw.split(";");
        return Arrays.asList(values);
    }
}
