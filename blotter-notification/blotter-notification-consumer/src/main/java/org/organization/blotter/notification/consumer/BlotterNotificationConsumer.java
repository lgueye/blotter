package org.organization.blotter.notification.consumer;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author louis.gueye@gmail.com
 */
@RequiredArgsConstructor
public class BlotterNotificationConsumer {
	private final Set<String> subscribers = Sets.newConcurrentHashSet();
	private final List<OrderNotificationDto> notifications = Lists.newArrayList();

	public void subscribe(String user) {
		subscribers.add(user);
	}

	public void unsubscribe(String user) {
		subscribers.remove(user);
	}

	public List<OrderNotificationDto> getNotifications(String subscriber) {
		final Optional<String> subscribed = subscribers.stream().filter(s -> s.equals(subscriber)).findFirst();
		return subscribed.isPresent() ? notifications : Collections.emptyList();
	}

	@KafkaListener(topics = "orders-notifications", groupId = "orders")
	public void onMessage(OrderNotificationDto message) {
		notifications.add(message);
	}
}
