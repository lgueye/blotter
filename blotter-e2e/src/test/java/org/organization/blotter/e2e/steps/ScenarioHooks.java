package org.organization.blotter.e2e.steps;

import io.cucumber.java.Before;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

/**
 * @author louis.gueye@gmail.com
 */
@Scope(SCOPE_CUCUMBER_GLUE)
@RequiredArgsConstructor
@Slf4j
public class ScenarioHooks {

	private final DataSource dataSource;

	@Before
	public void beforeScenario() {
		log.info("Before scenario");
		try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
			final int executed = statement.executeUpdate("DELETE FROM normalized_orders");
			if (executed > 0)
				log.info("Truncated schema");
		} catch (Exception e) {
			log.error("Failed to truncate schema", e);
		}
	}

}
