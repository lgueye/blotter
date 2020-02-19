package org.organization.blotter.notification.producer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.organizarion.blotter.notification.model.OrderNotificationDto;
import org.organization.blotter.shared.model.OrderStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

/**
 * @author louis.gueye@gmail.com
 */
@RunWith(MockitoJUnitRunner.class)
public class BlotterNotificationProducerTest {
	@Mock
	private KafkaTemplate<String, OrderNotificationDto> kafkaTemplate;
	private String destination = "foo";

	private BlotterNotificationProducer underTest;

	@Before
	public void before() {
		underTest = new BlotterNotificationProducer(destination, kafkaTemplate);
	}

	@Test
	public void send_ok() {
		// Given
		final OrderNotificationDto notification = OrderNotificationDto.builder().status(OrderStatus.validated).build();
		final ArgumentCaptor<Message> argumentCaptor = ArgumentCaptor.forClass(Message.class);

		// When
		underTest.send(notification);

		// Then
		verify(kafkaTemplate).send(argumentCaptor.capture());
		final Message<OrderNotificationDto> captured = argumentCaptor.getValue();
		assertEquals(notification, captured.getPayload());
		final MessageHeaders headers = captured.getHeaders();
		assertTrue(headers.entrySet().stream().anyMatch(entry -> entry.getKey().equals(KafkaHeaders.TOPIC) && entry.getValue().equals(destination)));
	}
}
