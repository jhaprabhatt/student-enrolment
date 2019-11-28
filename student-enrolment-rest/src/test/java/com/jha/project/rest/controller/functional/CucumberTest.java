package com.jha.project.rest.controller.functional;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty"}
        , features = {"classpath:com.jha.project.cucumber"}
        , glue = {"com.jha.project.rest.controller.functional"}
        , tags = "@studentenroll")
public class CucumberTest {
}
