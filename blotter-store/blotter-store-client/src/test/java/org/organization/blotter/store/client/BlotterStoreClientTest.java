package org.organization.blotter.store.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.organization.blotter.api.model.NormalizedOrderDto;
import org.organization.blotter.shared.model.MetaType;
import org.organization.blotter.shared.model.OrderStatus;
import org.organization.blotter.shared.model.TradeIntent;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author louis.gueye@gmail.com
 */
@RunWith(MockitoJUnitRunner.class)
public class BlotterStoreClientTest {
	@Mock
	private NormalizedOrderRepository repository;

	@InjectMocks
	private BlotterStoreClient underTest;

	@Test
	public void save_ok() {
		// Given
		final float amount = 45.6f;
		final String author = "any-author";
		final String details = "{}";
		final String externalIdentifier = "ext-id";
		final String id = "id";
		final String instrument = "instrument";
		final TradeIntent intent = TradeIntent.sell;
		final MetaType metaType = MetaType.stex;
		final String portfolio = "portfolio";
		final OrderStatus status = OrderStatus.validated;
		final Instant now = Instant.now();
		final NormalizedOrderDto dto = NormalizedOrderDto.builder() //
				.amount(amount) //
				.author(author) //
				.details(details) //
				.externalIdentifier(externalIdentifier) //
				.id(id) //
				.instrument(instrument) //
				.intent(intent) //
				.metaType(metaType) //
				.portfolio(portfolio) //
				.status(status) //
				.timestamp(now) //
				.build();

		when(repository.findOne(ArgumentMatchers.any())).thenReturn(Optional.empty());
		final ArgumentCaptor<NormalizedOrder> argumentCaptor = ArgumentCaptor.forClass(NormalizedOrder.class);

		// When
		underTest.save(dto);

		// Then
		verify(repository).save(argumentCaptor.capture());
		final NormalizedOrder captured = argumentCaptor.getValue();
		assertEquals(amount, Float.valueOf(captured.getAmount()));
		assertEquals(author, captured.getAuthor());
		assertEquals(details, captured.getDetails());
		assertEquals(externalIdentifier, captured.getExternalIdentifier());
		assertNotEquals(id, captured.getId());
		assertEquals(instrument, captured.getInstrument());
		assertEquals(intent, captured.getIntent());
		assertEquals(metaType, captured.getMetaType());
		assertEquals(portfolio, captured.getPortfolio());
		assertEquals(status, captured.getStatus());
		assertEquals(now, captured.getTimestamp());
	}

	@Test
	public void update_ok() {
		// Given
		final float amount = 45.6f;
		final String author = "any-author";
		final String details = "{}";
		final String externalIdentifier = "ext-id";
		final String id = "id";
		final String instrument = "instrument";
		final TradeIntent intent = TradeIntent.sell;
		final MetaType metaType = MetaType.stex;
		final String portfolio = "portfolio";
		final OrderStatus status = OrderStatus.validated;
		final Instant now = Instant.now();
		final NormalizedOrderDto dto = NormalizedOrderDto.builder() //
				.amount(amount) //
				.author(author) //
				.details(details) //
				.externalIdentifier(externalIdentifier) //
				.id(id) //
				.instrument(instrument) //
				.intent(intent) //
				.metaType(metaType) //
				.portfolio(portfolio) //
				.status(status) //
				.timestamp(now) //
				.build();

		when(repository.findOne(ArgumentMatchers.any())).thenReturn(Optional.of(NormalizedOrder.builder().id(id).build()));
		final ArgumentCaptor<NormalizedOrder> argumentCaptor = ArgumentCaptor.forClass(NormalizedOrder.class);

		// When
		underTest.save(dto);

		// Then
		verify(repository).save(argumentCaptor.capture());
		final NormalizedOrder captured = argumentCaptor.getValue();
		assertEquals(amount, Float.valueOf(captured.getAmount()));
		assertEquals(author, captured.getAuthor());
		assertEquals(details, captured.getDetails());
		assertEquals(externalIdentifier, captured.getExternalIdentifier());
		assertEquals(id, captured.getId());
		assertEquals(instrument, captured.getInstrument());
		assertEquals(intent, captured.getIntent());
		assertEquals(metaType, captured.getMetaType());
		assertEquals(portfolio, captured.getPortfolio());
		assertEquals(status, captured.getStatus());
		assertEquals(now, captured.getTimestamp());

	}

}
