package org.organization.blotter.store.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author louis.gueye@gmail.com
 */
@Configuration
public class BlotterStoreClientConfiguration {
	@Bean
	public BlotterStoreClient blotterStoreClient() {
		return new BlotterStoreClient();
	}
}
