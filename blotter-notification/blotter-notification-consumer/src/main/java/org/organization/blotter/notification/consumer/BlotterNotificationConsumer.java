package org.organization.blotter.notification.consumer;

import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.organizarion.blotter.notification.model.NotificationTopic;
import org.organizarion.blotter.notification.model.OrderNotificationDto;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author louis.gueye@gmail.com
 */
@RequiredArgsConstructor
@Slf4j
public class BlotterNotificationConsumer {
	private final Set<String> subscribers = Sets.newConcurrentHashSet();
	private final Set<OrderNotificationDto> notifications = Sets.newConcurrentHashSet();

	public void subscribe(String user) {
		subscribers.add(user);
		// log.info("{} subscribed to notifications", user);
	}

	Set<String> getSubscribers() {
		return subscribers;
	}

	Set<OrderNotificationDto> getNotifications() {
		return notifications;
	}

	public void unsubscribe(String user) {
		subscribers.remove(user);
	}

	public List<OrderNotificationDto> getNotifications(String subscriber) {
		final Optional<String> subscribed = subscribers.stream().filter(s -> s.equals(subscriber)).findFirst();
		if (subscribed.isPresent()) {
			// log.info("{} found among subscribers", subscriber);
			return notifications.stream().sorted(Comparator.comparing(OrderNotificationDto::getTimestamp)).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@KafkaListener(topics = NotificationTopic.ORDER_STATUS, groupId = "ATH")
	public void onMessage(OrderNotificationDto message) {
		final boolean added = notifications.add(message);
		if (added)
			log.info("Added {} to notifications store", message);
	}
}
