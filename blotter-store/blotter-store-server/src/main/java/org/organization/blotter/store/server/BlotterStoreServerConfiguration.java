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
	public H2Server h2Server(@Value("${blotter.store.server.port}") final Integer port,
			@Value("${blotter.store.server.base-dir}") final String baseDir) {
		return new H2Server(port, baseDir);
	}
}
