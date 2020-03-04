package org.organization.blotter.api.server.normalized;

import org.junit.Before;
import org.junit.Test;
import org.organizarion.blotter.notification.model.OrderNotificationDto;
import org.organization.blotter.api.model.NormalizedOrderDto;
import org.organization.blotter.shared.model.MetaType;
import org.organization.blotter.shared.model.OrderStatus;
import org.organization.blotter.shared.model.TradeIntent;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author louis.gueye@gmail.com
 */
public class OrderNotificationDtoProducerTest {
	private OrderNotificationDtoProducer underTest;

	@Before
	public void before() {
		underTest = new OrderNotificationDtoProducer();
	}

	@Test
	public void convert_ok() {
		// Given
		final Instant now = Instant.now();
		final OrderStatus status = OrderStatus.placed;
		final MetaType metaType = MetaType.fx;
		final String portfolio = "pf-0002";
		final TradeIntent intent = TradeIntent.sell;
		final String instrument = "LUAXXXXXXX";
		final String externalIdentifier = "ext-id";
		final String author = "louis";
		final float amount = 450000.00f;
		final String id = UUID.randomUUID().toString();
		final String details = "{}";
		final NormalizedOrderDto normalizedOrderDto = NormalizedOrderDto.builder() //
				.metaType(metaType) //
				.timestamp(now) //
				.status(status) //
				.portfolio(portfolio) //
				.intent(intent) //
				.instrument(instrument) //
				.externalIdentifier(externalIdentifier) //
				.author(author) //
				.amount(amount) //
				.id(id) //
				.details(details).build();

		// When
		final OrderNotificationDto actual = underTest.convert(normalizedOrderDto);

		assertNotNull(actual);
		assertEquals(metaType, actual.getMetaType());
		assertEquals(now, actual.getTimestamp());
		assertEquals(status, actual.getStatus());
		assertEquals(portfolio, actual.getPortfolio());
		assertEquals(intent, actual.getIntent());
		assertEquals(instrument, actual.getInstrument());
		assertEquals(externalIdentifier, actual.getExternalIdentifier());
		assertEquals(author, actual.getAuthor());
		assertEquals(amount, actual.getAmount());
		assertEquals(id, actual.getId());
	}
}
