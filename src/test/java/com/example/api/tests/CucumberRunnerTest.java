package com.example.api.tests;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.example.api.tests.steps", "com.example.api.tests"},
        plugin = {"pretty"}
)
public class CucumberRunnerTest {
}