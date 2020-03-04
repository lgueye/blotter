package org.organization.blotter.api.server.normalized;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import or.organization.blotter.broker.model.SourceQueues;
import or.organization.blotter.broker.model.smarttrade.SmartTradeFxOrderDto;
import org.organization.blotter.api.model.NormalizedOrderDto;
import org.organization.blotter.broker.consumer.ProcessingContext;
import org.organization.blotter.shared.model.MetaType;

import java.util.Set;

/**
 * @author louis.gueye@gmail.com
 */
@RequiredArgsConstructor
public class SmartTradeFxOrderDtoToNormalizedOrderDtoProducer implements NormalizedOrderDtoProducer {

	private final ObjectMapper objectMapper;

	private static final Set<MetaType> SUPPORTED_METATYPES = Sets.newHashSet(MetaType.fx);

	@Override
	public boolean accept(final ProcessingContext context) {
		if (context == null || !SourceQueues.SMART_TRADE.equals(context.getSource())) {
			return false;
		}
		try {
			SmartTradeFxOrderDto order = objectMapper.readValue(context.getMessage(), SmartTradeFxOrderDto.class);
			if (order == null) {
				return false;
			}
			// more checking and conditions here
			return SUPPORTED_METATYPES.contains(order.getMetaType());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public NormalizedOrderDto produce(final ProcessingContext context) {
		try {
			SmartTradeFxOrderDto order = objectMapper.readValue(context.getMessage(), SmartTradeFxOrderDto.class);
			return NormalizedOrderDto.builder() //
					.amount(order.getAmount()) //
					.author(order.getAuthor()) //
					.externalIdentifier(order.getExternalIdentifier()) //
					.instrument(order.getInstrument()) //
					.metaType(MetaType.fx) //
					.intent(order.getIntent()) //
					.portfolio(order.getPortfolio()) //
					.status(order.getStatus()) //
					.timestamp(context.getTimestamp()) //
					.details(objectMapper.writeValueAsString(order)).build();
		} catch (Exception e) {
			throw new IllegalStateException("");
		}
	}
}
