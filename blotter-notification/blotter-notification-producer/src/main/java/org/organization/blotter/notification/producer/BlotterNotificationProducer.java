package org.organization.blotter.notification.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.organizarion.blotter.notification.model.OrderNotificationDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @author louis.gueye@gmail.com
 */
@RequiredArgsConstructor
@Slf4j
public class BlotterNotificationProducer {
	private final String destination;
	private final KafkaTemplate<String, OrderNotificationDto> kafkaTemplate;

	public void send(OrderNotificationDto notification) {
		final Message<OrderNotificationDto> message = MessageBuilder.withPayload(notification).setHeader(KafkaHeaders.TOPIC, destination).build();
		kafkaTemplate.send(message);
		// log.info("Sent data='{}' to topic='{}'", notification, destination);
	}
}
