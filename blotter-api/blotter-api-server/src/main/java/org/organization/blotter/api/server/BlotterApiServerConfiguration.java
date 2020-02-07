package org.organization.blotter.api.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.organization.blotter.api.server.normalized.NormalizedOrderDtoProducer;
import org.organization.blotter.api.server.normalized.OrderNotificationDtoProducer;
import org.organization.blotter.api.server.specific.stex.StextOrderDtoToNormalizedOrderDtoProducer;
import org.organization.blotter.broker.consumer.BlotterBrokerConsumerConfiguration;
import org.organization.blotter.notification.producer.BlotterNotificationProducer;
import org.organization.blotter.notification.producer.BlotterNotificationProducerConfiguration;
import org.organization.blotter.store.client.BlotterStoreClient;
import org.organization.blotter.store.client.BlotterStoreClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;

/**
 * @author louis.gueye@gmail.com
 */
@Configuration
@Import({BlotterBrokerConsumerConfiguration.class, BlotterNotificationProducerConfiguration.class, BlotterStoreClientConfiguration.class})
public class BlotterApiServerConfiguration {

	@Bean
	public StextOrderDtoToNormalizedOrderDtoProducer stextOrderDtoToNormalizedOrderDtoProducer(final ObjectMapper objectMapper) {
		return new StextOrderDtoToNormalizedOrderDtoProducer(objectMapper);
	}

	@Bean
	public OrderNotificationDtoProducer orderNotificationDtoProducer() {
		return new OrderNotificationDtoProducer();
	}

	@Bean
	public NormalizationProcessor normalizationProcessor(final List<NormalizedOrderDtoProducer> normalizedOrderProducers,
			final BlotterStoreClient persistenceService, final OrderNotificationDtoProducer orderNotificationProducer,
			final BlotterNotificationProducer orderNotificationService) {
		return new NormalizationProcessor(normalizedOrderProducers, persistenceService, orderNotificationProducer, orderNotificationService);
	}

	@Bean
	public DetailsProcessor detailsProcessor() {
		return new DetailsProcessor();
	}
}
