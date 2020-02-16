package org.organization.blotter.e2e.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import or.organization.blotter.broker.model.avaloq.AvaloqFxOrderDto;
import or.organization.blotter.broker.model.avaloq.AvaloqStexOrderDto;
import or.organization.blotter.broker.model.SourceQueues;
import or.organization.blotter.broker.model.avaloq.SmartTradeFxOrderDto;
import org.organization.blotter.broker.producer.BlotterBrokerProducer;
import org.organization.blotter.shared.model.MetaType;

import java.util.List;
import java.util.Map;

/**
 * @author louis.gueye@gmail.com
 */
public class BlotterBrokerProducerSteps implements En {

	public BlotterBrokerProducerSteps(final BlotterBrokerProducer blotterBrokerProducer, final ObjectMapper objectMapper) {
		DataTableType((Map<String, String> row) -> objectMapper.convertValue(row, AvaloqStexOrderDto.class));
		DataTableType((Map<String, String> row) -> objectMapper.convertValue(row, AvaloqFxOrderDto.class));
		DataTableType((Map<String, String> row) -> objectMapper.convertValue(row, SmartTradeFxOrderDto.class));

		Given("blotter system receives {} orders from {}", (final MetaType metaType, final String messageSource, final DataTable datatable) -> {
			switch (messageSource) {
				case SourceQueues.AVALOQ :
					switch (metaType) {
						case stex :
							List<AvaloqStexOrderDto> avaloqStexOrderDtos = datatable.asList(AvaloqStexOrderDto.class);
							avaloqStexOrderDtos.forEach(blotterBrokerProducer::send);
							break;
						case fx :
							List<AvaloqFxOrderDto> avaloqFxOrderDtos = datatable.asList(AvaloqFxOrderDto.class);
							avaloqFxOrderDtos.forEach(blotterBrokerProducer::send);
							break;
					}
				case SourceQueues.SMART_TRADE :
					switch (metaType) {
						case fx :
							List<SmartTradeFxOrderDto> smartTradeFxOrderDtos = datatable.asList(SmartTradeFxOrderDto.class);
							smartTradeFxOrderDtos.forEach(blotterBrokerProducer::send);
							break;
					}
			}
		});
	}

}
