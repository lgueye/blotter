package org.organization.blotter.e2e.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import org.assertj.core.util.Lists;
import org.awaitility.Awaitility;
import org.organization.blotter.api.consumer.BlotterApiConsumer;
import org.organization.blotter.api.consumer.OrderReadDto;
import org.organization.blotter.api.consumer.SearchOrderCriteria;
import org.organization.blotter.e2e.BlotterE2EConfiguration;
import org.organization.blotter.notification.consumer.OrderNotificationDto;
import org.springframework.test.context.ContextConfiguration;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author louis.gueye@gmail.com
 *
 * NOTE/ only one @ContextConfiguration declaration is allowed through all steps definitions
 */
@ContextConfiguration(classes = BlotterE2EConfiguration.class)
public class BlotterApiConsumerSteps implements En {

	private List<SearchOrderCriteria> criteria = Lists.newArrayList();

	public BlotterApiConsumerSteps(final BlotterApiConsumer blotterApiConsumer, final ObjectMapper objectMapper) {

		DataTableType((Map<String, String> row) -> objectMapper.convertValue(row, SearchOrderCriteria.class));

		When("{} searches for orders by criteria",
				(final String user, DataTable dataTable) -> this.criteria = dataTable.asList(SearchOrderCriteria.class));

		Then("within {}, the below orders should be found",
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
