package org.organization.blotter.e2e.steps;

import io.cucumber.java8.En;
import org.awaitility.Awaitility;
import org.organization.blotter.notification.consumer.BlotterNotificationConsumer;
import org.organization.blotter.notification.consumer.OrderNotificationDto;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author louis.gueye@gmail.com
 */
public class BlotterNotificationConsumerSteps implements En {

	public BlotterNotificationConsumerSteps(final BlotterNotificationConsumer blotterNotificationConsumer) {
		Given("{} subscribes to orders notifications", blotterNotificationConsumer::subscribe);

		Then("within {}, {} should be notified of the below orders notifications:",
				(final String durationAsString, final String user, final List<OrderNotificationDto> expected) -> {
					final Duration timeout = Duration.parse(durationAsString);
					Awaitility.await().atMost(timeout.toMillis(), TimeUnit.MILLISECONDS).pollDelay(50, TimeUnit.MILLISECONDS)
							.pollInterval(50, TimeUnit.MILLISECONDS).until(() -> {
								final List<OrderNotificationDto> actual = blotterNotificationConsumer.getNotifications(user);
								return expected.equals(actual);
							});
				});

	}
}
