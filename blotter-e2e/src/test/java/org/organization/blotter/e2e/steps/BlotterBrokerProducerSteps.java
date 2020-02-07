package org.organization.blotter.e2e.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import org.organization.blotter.broker.producer.BlotterBrokerProducer;
import org.organization.blotter.broker.producer.RawStexDto;

import java.util.List;
import java.util.Map;

/**
 * @author louis.gueye@gmail.com
 */
public class BlotterBrokerProducerSteps implements En {

	public BlotterBrokerProducerSteps(final BlotterBrokerProducer blotterBrokerProducer, final ObjectMapper objectMapper) {
		DataTableType((Map<String, String> row) -> objectMapper.convertValue(row, RawStexDto.class));

		Given("blotter system receives the messages below from {}", (final String messageSource, final DataTable datatable) -> {
			final List<RawStexDto> list = datatable.asList(RawStexDto.class);
			list.forEach(message -> blotterBrokerProducer.send(messageSource, message));
		});
	}

}
