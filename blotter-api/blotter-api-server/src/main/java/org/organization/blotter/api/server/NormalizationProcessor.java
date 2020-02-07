package org.organization.blotter.api.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.organizarion.blotter.notification.model.OrderNotificationDto;
import org.organization.blotter.api.model.NormalizedOrderDto;
import org.organization.blotter.api.server.normalized.NormalizedOrderDtoProducer;
import org.organization.blotter.api.server.normalized.OrderNotificationDtoProducer;
import org.organization.blotter.broker.consumer.IncomingMessageProcessor;
import org.organization.blotter.broker.consumer.ProcessingContext;
import org.organization.blotter.notification.producer.BlotterNotificationProducer;
import org.organization.blotter.store.client.BlotterStoreClient;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author louis.gueye@gmail.com
 */
@RequiredArgsConstructor
@Slf4j
public class NormalizationProcessor implements IncomingMessageProcessor {

	private final List<NormalizedOrderDtoProducer> normalizedOrderProducers;
	private final BlotterStoreClient persistenceService;
	private final OrderNotificationDtoProducer orderNotificationProducer;
	private final BlotterNotificationProducer orderNotificationService;

	@Override
	public void process(final ProcessingContext context) {

		final List<NormalizedOrderDto> orders = normalizedOrderProducers.stream() //
				.filter(producer -> producer.accept(context)) //
				.map(producer -> producer.produce(context)) //
				.collect(Collectors.toList());// produce normalized order
		log.info("Produced {} normalized orders from context {}", orders.size(), context);
		orders.forEach(order -> {
			persistenceService.save(order);
			log.info("Persisted order {}", order);
		});// persist normalized order

		orders.stream().map(order -> {
			final OrderNotificationDto notification = orderNotificationProducer.convert(order);
			log.info("produced notification {} from order {}", notification, order);
			return notification;
		}) // produce notification order
				.filter(Objects::nonNull).forEach(notification -> {
					orderNotificationService.produce(notification);
					log.info("Sent notification {} to notification broker", notification);
				}); // notify notification order consumers
	}
}
