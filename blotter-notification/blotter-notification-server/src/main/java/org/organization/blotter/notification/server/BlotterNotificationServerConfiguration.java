package org.organization.blotter.notification.server;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.test.EmbeddedKafkaBroker;

/**
 * @author louis.gueye@gmail.com
 */
@Configuration
public class BlotterNotificationServerConfiguration {

	@Bean
	public EmbeddedKafkaBroker embeddedKafkaBroker(@Value("${blotter.notification.server.port}") final int port) {
		final EmbeddedKafkaBroker kafkaBroker = new EmbeddedKafkaBroker(1, true);
		kafkaBroker.kafkaPorts(port);
		return kafkaBroker;
	}
}
