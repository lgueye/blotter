package org.organization.blotter.store.migrations.jobs;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by <a href="mailto:louis.gueye@domo-safety.com">Louis Gueye</a>.
 */
@Slf4j
@Component
@Order(3)
public class MigrateJob implements CommandLineRunner {
	private final Flyway flyway;
	private final boolean migrate;

	public MigrateJob(@Autowired final Flyway flyway, @Value("${flyway.migrate:true}") final boolean migrate) {
		this.flyway = flyway;
		this.migrate = migrate;
	}

	@Override
	public void run(String... args) {
		if (migrate) {
			flyway.migrate();
		}

	}
}
