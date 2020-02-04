package org.organization.blotter.e2e;

import org.organization.blotter.api.consumer.BlotterApiConsumerConfiguration;
import org.organization.blotter.broker.producer.BlotterBrokerProducerConfiguration;
import org.organization.blotter.notification.consumer.BlotterNotificationConsumerConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author louis.gueye@gmail.com
 */
@Import({BlotterApiConsumerConfiguration.class, BlotterNotificationConsumerConfiguration.class, BlotterBrokerProducerConfiguration.class})
@Configuration
public class BlotterE2EConfiguration {
}
