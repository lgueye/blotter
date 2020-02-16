package org.organization.blotter.e2e.steps;

import io.cucumber.java.Before;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;

import javax.sql.DataSource;
import java.sql.SQLException;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

/**
 * @author louis.gueye@gmail.com
 */
@Scope(SCOPE_CUCUMBER_GLUE)
@RequiredArgsConstructor
public class ScenarioHooks {

	private final DataSource dataSource;

	@Before
	public void beforeScenario() throws SQLException {
		dataSource.getConnection().prepareStatement("delete from normalized_orders;").execute();
	}

}
