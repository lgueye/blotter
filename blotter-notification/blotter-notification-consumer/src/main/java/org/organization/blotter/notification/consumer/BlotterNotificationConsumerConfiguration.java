package org.organization.blotter.notification.consumer;

/**
 * @author louis.gueye@gmail.com
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BlotterNotificationConsumerConfiguration {
	@Bean
	public BlotterNotificationConsumer blotterNotificationConsumer() {
		return new BlotterNotificationConsumer();
	}

}
