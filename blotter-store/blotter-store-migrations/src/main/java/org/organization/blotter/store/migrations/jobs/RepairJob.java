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
@Order(1)
public class RepairJob implements CommandLineRunner {
	private final Flyway flyway;
	private final boolean repair;

	public RepairJob(@Autowired final Flyway flyway, @Value("${flyway.repair:false}") final boolean repair) {
		this.flyway = flyway;
		this.repair = repair;
	}

	@Override
	public void run(String... args) {
		if (repair) {
			flyway.repair();
		}
	}
}
