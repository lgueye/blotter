package org.organization.blotter.store.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author louis.gueye@gmail.com
 */
@Configuration
public class BlotterStoreServerConfiguration {

	@Bean
	public MySQLServer mySQLServer(@Value("${blotter.store.server.port}") final Integer port,
			@Value("${blotter.store.server.schema}") final String schema, @Value("${blotter.store.server.user}") final String user,
			@Value("${blotter.store.server.password}") final String password) {
		return new MySQLServer(port, schema, user, password);
	}
}
