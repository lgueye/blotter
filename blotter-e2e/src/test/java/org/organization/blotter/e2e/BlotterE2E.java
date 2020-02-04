package org.organization.blotter.e2e;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * @author louis.gueye@gmail.com
 */
@RunWith(Cucumber.class)
@CucumberOptions(strict = true, features = {"classpath:features/"}, plugin = {"pretty"})
public class BlotterE2E {
}
