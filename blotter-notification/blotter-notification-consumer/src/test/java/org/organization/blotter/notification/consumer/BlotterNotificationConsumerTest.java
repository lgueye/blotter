package org.organization.blotter.notification.consumer;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.organizarion.blotter.notification.model.OrderNotificationDto;

import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author louis.gueye@gmail.com
 */
public class BlotterNotificationConsumerTest {
	private BlotterNotificationConsumer underTest;

	@Before
	public void before() {
		underTest = new BlotterNotificationConsumer();
	}

	@Test
	public void subscribe_ok() {
		// Given
		final String subscriber = "app-01";

		// When
		underTest.subscribe(subscriber);

		// Then
		assertTrue(underTest.getSubscribers().contains(subscriber));
	}

	@Test
	public void unsubscribe_ok() {
		// Given
		final String subscriber = "app-01";
		underTest.subscribe(subscriber);
		underTest.subscribe("app-02");

		// When
		underTest.unsubscribe("app-01");

		// Then
		assertEquals(Sets.newHashSet("app-02"), underTest.getSubscribers());
	}

	@Test
	public void on_message_ok() {
		// Given
		final OrderNotificationDto notification = OrderNotificationDto.builder().build();

		// When
		underTest.onMessage(notification);

		// Then
		assertSame(notification, underTest.getNotifications().iterator().next());
	}

	@Test
	public void get_subscriber_notifications() {
		// Given
		final Instant now = Instant.now();
		underTest.onMessage(OrderNotificationDto.builder().portfolio("pf1").timestamp(now).build());
		underTest.onMessage(OrderNotificationDto.builder().portfolio("pf2").timestamp(now.plus(Duration.ofSeconds(5))).build());
		underTest.subscribe("foo");

		// Then
		assertEquals(2, underTest.getNotifications("foo").size());
	}

	@Test
	public void get_guest_notifications() {
		// Given
		underTest.onMessage(OrderNotificationDto.builder().portfolio("pf1").build());
		underTest.onMessage(OrderNotificationDto.builder().portfolio("pf2").build());

		// Then
		assertEquals(0, underTest.getNotifications("guest").size());
	}
}
