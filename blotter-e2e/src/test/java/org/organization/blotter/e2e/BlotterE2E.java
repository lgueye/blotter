package org.organization.blotter.e2e;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * @author louis.gueye@gmail.com
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = {"classpath:features/"}, plugin = {"pretty"}, glue = {"org.organization.blotter.broker.e2e.BlotterE2EConfiguration"})
public class BlotterE2E {
}
