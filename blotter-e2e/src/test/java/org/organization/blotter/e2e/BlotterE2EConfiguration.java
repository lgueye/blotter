package org.organization.blotter.e2e;

import org.organization.blotter.api.consumer.BlotterApiConsumer;
import org.organization.blotter.api.consumer.BlotterApiConsumerConfiguration;
import org.organization.blotter.broker.producer.BlotterBrokerProducer;
import org.organization.blotter.broker.producer.BlotterBrokerProducerConfiguration;
import org.organization.blotter.e2e.steps.BlotterApiConsumerSteps;
import org.organization.blotter.e2e.steps.BlotterBrokerProducerSteps;
import org.organization.blotter.e2e.steps.BlotterNotificationConsumerSteps;
import org.organization.blotter.notification.consumer.BlotterNotificationConsumer;
import org.organization.blotter.notification.consumer.BlotterNotificationConsumerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author louis.gueye@gmail.com
 */
@Import({BlotterApiConsumerConfiguration.class, BlotterNotificationConsumerConfiguration.class, BlotterBrokerProducerConfiguration.class})
@Configuration
public class BlotterE2EConfiguration {

	@Bean
	public BlotterBrokerProducerSteps blotterBrokerProducerSteps(final BlotterBrokerProducer blotterBrokerProducer) {
		return new BlotterBrokerProducerSteps(blotterBrokerProducer);
	}

	@Bean
	public BlotterApiConsumerSteps blotterApiConsumerSteps(final BlotterApiConsumer blotterApiConsumer) {
		return new BlotterApiConsumerSteps(blotterApiConsumer);
	}

	@Bean
	public BlotterNotificationConsumerSteps blotterNotificationConsumerSteps(final BlotterNotificationConsumer blotterNotificationConsumer) {
		return new BlotterNotificationConsumerSteps(blotterNotificationConsumer);
	}
}
