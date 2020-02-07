package org.organization.blotter.e2e.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.awaitility.Awaitility;
import org.organization.blotter.api.consumer.BlotterApiConsumer;
import org.organization.blotter.api.model.OrderReadDto;
import org.organization.blotter.api.model.SearchOrderCriteria;
import org.organization.blotter.e2e.BlotterE2EConfiguration;
import org.springframework.test.context.ContextConfiguration;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author louis.gueye@gmail.com
 *
 * NOTE: only one @ContextConfiguration declaration is allowed through all steps definitions
 */
@ContextConfiguration(classes = BlotterE2EConfiguration.class)
@Slf4j
public class BlotterApiConsumerSteps implements En {

	private List<SearchOrderCriteria> criteria = Lists.newArrayList();

	public BlotterApiConsumerSteps(final BlotterApiConsumer blotterApiConsumer, final ObjectMapper objectMapper) {

		DataTableType((Map<String, String> row) -> objectMapper.convertValue(row, SearchOrderCriteria.class));
		DataTableType((Map<String, String> row) -> objectMapper.convertValue(row, OrderReadDto.class));

		When("{} searches for orders by criteria",
				(final String user, final DataTable dataTable) -> this.criteria = dataTable.asList(SearchOrderCriteria.class));

		Then("within {}, the below orders should be found",
				(final String durationAsString, final DataTable dataTable) -> {
					final List<OrderReadDto> expected = dataTable.asList(OrderReadDto.class);
					final Duration timeout = Duration.parse(durationAsString);
					Awaitility.await().atMost(timeout.toMillis(), TimeUnit.MILLISECONDS).pollDelay(50, TimeUnit.MILLISECONDS)
							.pollInterval(50, TimeUnit.MILLISECONDS).until(() -> {
								final List<OrderReadDto> actual = blotterApiConsumer.findByCriteria(criteria.iterator().next());
//								log.info("expected => {}", expected);
//								log.info("actual => {}", actual);
								return expected.equals(actual);
							});
				});

	}
}
