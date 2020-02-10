package org.organization.blotter.store.migrations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;

@SpringBootApplication(exclude = {JpaRepositoriesAutoConfiguration.class})
public class StoreUpgrader {

	public static void main(String[] args) {
		SpringApplication.run(StoreUpgrader.class, args);
	}

}
