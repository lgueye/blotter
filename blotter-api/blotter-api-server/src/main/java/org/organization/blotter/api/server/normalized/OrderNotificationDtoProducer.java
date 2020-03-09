package org.organization.blotter.api.server.normalized;

import org.organizarion.blotter.notification.model.OrderNotificationDto;
import org.organization.blotter.api.model.NormalizedOrderDto;
import org.springframework.core.convert.converter.Converter;

/**
 * @author louis.gueye@gmail.com
 */
public class OrderNotificationDtoProducer implements Converter<NormalizedOrderDto, OrderNotificationDto> {
	@Override
	public OrderNotificationDto convert(final NormalizedOrderDto normalizedOrderDto) {
		return OrderNotificationDto.builder() //
				.price(normalizedOrderDto.getPrice()) //
				.author(normalizedOrderDto.getAuthor()) //
				.externalIdentifier(normalizedOrderDto.getExternalIdentifier()) //
				.id(normalizedOrderDto.getId()) //
				.instrument(normalizedOrderDto.getInstrument()) //
				.intent(normalizedOrderDto.getIntent()) //
				.metaType(normalizedOrderDto.getMetaType()) //
				.portfolio(normalizedOrderDto.getPortfolio()) //
				.status(normalizedOrderDto.getStatus()) //
				.timestamp(normalizedOrderDto.getTimestamp()) //
				.build();
	}
}
