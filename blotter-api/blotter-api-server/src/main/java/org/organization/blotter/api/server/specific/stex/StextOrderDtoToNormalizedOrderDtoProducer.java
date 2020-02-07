package org.organization.blotter.api.server.specific.stex;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import org.organization.blotter.api.model.NormalizedOrderDto;
import org.organization.blotter.api.server.normalized.NormalizedOrderDtoProducer;
import org.organization.blotter.broker.consumer.ProcessingContext;
import org.organization.blotter.shared.model.MetaType;

import java.util.Set;

/**
 * @author louis.gueye@gmail.com
 */
@RequiredArgsConstructor
public class StextOrderDtoToNormalizedOrderDtoProducer implements NormalizedOrderDtoProducer {

	private final ObjectMapper objectMapper;

	private static final Set<MetaType> SUPPORTED_METATYPES = Sets.newHashSet(MetaType.stex);

	@Override
	public boolean accept(final ProcessingContext context) {
		try {
			StexOrderDto order = objectMapper.readValue(context.getMessage(), StexOrderDto.class);
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
			StexOrderDto order = objectMapper.readValue(context.getMessage(), StexOrderDto.class);
			return NormalizedOrderDto.builder() //
					.amount(order.getAmount()) //
					.author(order.getAuthor()) //
					.externalIdentifier(order.getExternalIdentifier()) //
					.instrument(order.getInstrument()) //
					.metaType(MetaType.stex) //
					.intent(order.getIntent()) //
					.portfolio(order.getPortfolio()) //
					.timestamp(context.getTimestamp()) //
					.build();
		} catch (Exception e) {
			throw new IllegalStateException("");
		}
	}
}
