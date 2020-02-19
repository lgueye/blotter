package org.organization.blotter.store.client;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.organization.blotter.api.model.NormalizedOrderDto;
import org.organization.blotter.api.model.OrderReadDto;
import org.organization.blotter.api.model.SearchOrderCriteria;
import org.organization.blotter.shared.model.MetaType;
import org.organization.blotter.shared.model.OrderStatus;
import org.organization.blotter.shared.model.TradeIntent;
import org.springframework.data.domain.Example;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
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

	@Test
	public void find_by_criteria_empty_results() {
		assertEquals(Collections.emptyList(), underTest.findByCriteria(null));
	}

	@Test
	public void find_by_criteria_ok() {
		// Given
		final SearchOrderCriteria criteria = SearchOrderCriteria.builder().portfolio("pf-001").metaType(MetaType.stex).build();
		final ArgumentCaptor<Example> argumentCaptor = ArgumentCaptor.forClass(Example.class);
		final float amount = 45.6f;
		final String o1Author = "any-author";
		final String details = "{}";
		final String o1ExternalIdentifier = "ext-id-01";
		final String o2ExternalIdentifier = "ext-id-01";
		final String o1Id = "id";
		final String o2Id = "id";
		final String instrument = "instrument";
		final TradeIntent intent = TradeIntent.sell;
		final MetaType metaType = MetaType.stex;
		final String portfolio = "portfolio";
		final OrderStatus status = OrderStatus.validated;
		final Instant o1Timestamp = Instant.now();
		final Instant o2Timestamp = o1Timestamp.plus(Duration.ofSeconds(5));
		final NormalizedOrder o1 = NormalizedOrder.builder() //
				.amount(String.valueOf(amount)) //
				.author(o1Author) //
				.details(details) //
				.externalIdentifier(o1ExternalIdentifier) //
				.id(o1Id) //
				.instrument(instrument) //
				.intent(intent) //
				.metaType(metaType) //
				.portfolio(portfolio) //
				.status(status) //
				.timestamp(o1Timestamp) //
				.build();
		final String o2Author = "louis";
		final float o2Amount = 88.2f;
		final NormalizedOrder o2 = NormalizedOrder.builder() //
				.amount(String.valueOf(o2Amount)) //
				.author(o2Author) //
				.details(details) //
				.externalIdentifier(o2ExternalIdentifier) //
				.id(o2Id) //
				.instrument(instrument) //
				.intent(intent) //
				.metaType(metaType) //
				.portfolio(portfolio) //
				.status(status) //
				.timestamp(o2Timestamp) //
				.build();

		when(repository.findAll(argumentCaptor.capture())).thenReturn(Lists.newArrayList(o1, o2));

		// When
		final List<OrderReadDto> actual = underTest.findByCriteria(criteria);

		final OrderReadDto r1 = OrderReadDto.builder() //
				.amount(amount) //
				.author(o1Author) //
				.externalIdentifier(o1ExternalIdentifier) //
				.instrument(instrument) //
				.intent(intent) //
				.metaType(metaType) //
				.portfolio(portfolio) //
				.status(status) //
				.timestamp(o1Timestamp) //
				.build();
		final OrderReadDto r2 = OrderReadDto.builder() //
				.amount(o2Amount) //
				.author(o2Author) //
				.externalIdentifier(o2ExternalIdentifier) //
				.instrument(instrument) //
				.intent(intent) //
				.metaType(metaType) //
				.portfolio(portfolio) //
				.status(status) //
				.timestamp(o2Timestamp) //
				.build();
		final List<OrderReadDto> expected = Lists.newArrayList(r1, r2);

		// Then
		assertEquals(expected, actual);
		verify(repository).findAll(argumentCaptor.capture());
		final Example captured = argumentCaptor.getValue();
		final NormalizedOrder actualCriteria = (NormalizedOrder) captured.getProbe();
		assertEquals(criteria.getPortfolio(), actualCriteria.getPortfolio());
		assertEquals(criteria.getMetaType(), actualCriteria.getMetaType());
		assertNull(actualCriteria.getAmount());
		assertNull(actualCriteria.getAuthor());
		assertNull(actualCriteria.getExternalIdentifier());
		assertNull(actualCriteria.getInstrument());
		assertNull(actualCriteria.getIntent());
		assertNull(actualCriteria.getStatus());
		assertNull(actualCriteria.getTimestamp());
	}
}
