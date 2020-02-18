package org.organization.blotter.e2e.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;
import org.organizarion.blotter.notification.model.OrderNotificationDto;
import org.organization.blotter.notification.consumer.BlotterNotificationConsumer;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author louis.gueye@gmail.com
 */
@Slf4j
public class BlotterNotificationConsumerSteps implements En {

	public BlotterNotificationConsumerSteps(final BlotterNotificationConsumer blotterNotificationConsumer, final ObjectMapper objectMapper) {

		DataTableType((Map<String, String> row) -> objectMapper.convertValue(row, OrderNotificationDto.class));

		Given("{} subscribes to orders notifications", blotterNotificationConsumer::subscribe);

		Then("within {}, {} should be notified of the below orders notifications",
				(final String durationAsString, final String user, final DataTable dataTable) -> {
					final Duration timeout = Duration.parse(durationAsString);
					final List<OrderNotificationDto> expected = dataTable.asList(OrderNotificationDto.class);
					Awaitility.await().atMost(timeout.toMillis(), TimeUnit.MILLISECONDS).pollDelay(50, TimeUnit.MILLISECONDS)
							.pollInterval(500, TimeUnit.MILLISECONDS).until(() -> {
								final List<OrderNotificationDto> actual = blotterNotificationConsumer.getNotifications(user);
								// log.info("actual => {}", actual);
								// log.info("expected => {}", expected);
									return expected.equals(actual);
								});
				});

	}
}
