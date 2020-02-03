package org.organization.blotter.e2e.steps;

import io.cucumber.java8.En;
import org.assertj.core.util.Lists;
import org.awaitility.Awaitility;
import org.organization.blotter.api.consumer.BlotterApiConsumer;
import org.organization.blotter.api.consumer.OrderReadDto;
import org.organization.blotter.api.consumer.SearchOrderCriteria;
import org.organization.blotter.notification.consumer.OrderNotificationDto;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author louis.gueye@gmail.com
 */
public class BlotterApiConsumerSteps implements En {
	private List<SearchOrderCriteria> criteria = Lists.newArrayList();
	public BlotterApiConsumerSteps(final BlotterApiConsumer blotterApiConsumer) {
		When("choisel searches for orders by criteria:", (final String user, List<SearchOrderCriteria> criteria) -> this.criteria = criteria);

		Then("within {}, the below orders should be found:",
				(final String durationAsString, final List<OrderNotificationDto> expected) -> {
					final Duration timeout = Duration.parse(durationAsString);
					Awaitility.await().atMost(timeout.toMillis(), TimeUnit.MILLISECONDS).pollDelay(50, TimeUnit.MILLISECONDS)
							.pollInterval(50, TimeUnit.MILLISECONDS).until(() -> {
								final List<OrderReadDto> actual = blotterApiConsumer.findByCriteria(criteria);
								return expected.equals(actual);
							});
				});

	}
}
