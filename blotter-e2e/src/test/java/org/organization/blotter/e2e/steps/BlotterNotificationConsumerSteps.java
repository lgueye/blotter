package org.organization.blotter.e2e.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import org.awaitility.Awaitility;
import org.organization.blotter.notification.consumer.BlotterNotificationConsumer;
import org.organization.blotter.notification.consumer.OrderNotificationDto;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author louis.gueye@gmail.com
 */
public class BlotterNotificationConsumerSteps implements En {

	public BlotterNotificationConsumerSteps(final BlotterNotificationConsumer blotterNotificationConsumer, final ObjectMapper objectMapper) {

		DataTableType((Map<String, String> row) -> objectMapper.convertValue(row, OrderNotificationDto.class));

		Given("{} subscribes to orders notifications", blotterNotificationConsumer::subscribe);

		Then("within {}, {} should be notified of the below orders notifications",
				(final String durationAsString, final String user, DataTable dataTable) -> {
					final Duration timeout = Duration.parse(durationAsString);
					Awaitility.await().atMost(timeout.toMillis(), TimeUnit.MILLISECONDS).pollDelay(50, TimeUnit.MILLISECONDS)
							.pollInterval(50, TimeUnit.MILLISECONDS).until(() -> {
								final List<OrderNotificationDto> actual = blotterNotificationConsumer.getNotifications(user);
								return dataTable.asList(OrderNotificationDto.class).equals(actual);
							});
				});

	}
}
