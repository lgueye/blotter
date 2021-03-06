package org.organization.blotter.store.client;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.organization.blotter.api.model.NormalizedOrderDto;
import org.organization.blotter.shared.model.MetaType;
import org.organization.blotter.shared.model.OrderStatus;
import org.organization.blotter.shared.model.TradeIntent;
import org.organization.blotter.store.client.parsers.CriterionToPredicateProducer;
import org.organization.blotter.store.client.parsers.in.MetaTypesCriteriaToPredicateProducer;
import org.organization.blotter.store.client.parsers.in.PortfoliosCriteriaToPredicateProducer;

import javax.persistence.EntityManager;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author louis.gueye@gmail.com
 */
@RunWith(MockitoJUnitRunner.class)
public class BlotterStoreClientTest {
	@Mock
	private NormalizedOrderRepository repository;
	@Mock
	private EntityManager entityManager;

	private BlotterStoreClient underTest;

	@Before
	public void before() {
		List<CriterionToPredicateProducer> predicateProducers = Lists.newArrayList(new PortfoliosCriteriaToPredicateProducer(),
				new MetaTypesCriteriaToPredicateProducer());
		underTest = new BlotterStoreClient(repository, entityManager, predicateProducers);
	}

	@Test
	public void save_ok() {
		// Given
		final float price = 45.6f;
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
		final Instant settlementDate = now.plus(Duration.ofDays(1));
		final NormalizedOrderDto dto = NormalizedOrderDto.builder() //
				.price(price) //
				.author(author) //
				.details(details) //
				.externalIdentifier(externalIdentifier) //
				.id(id) //
				.instrument(instrument) //
				.intent(intent) //
				.metaType(metaType) //
				.portfolio(portfolio) //
				.status(status) //
				.settlementDate(settlementDate) //
				.timestamp(now) //
				.build();

		when(repository.findOne(ArgumentMatchers.any())).thenReturn(Optional.empty());
		final ArgumentCaptor<NormalizedOrder> argumentCaptor = ArgumentCaptor.forClass(NormalizedOrder.class);

		// When
		underTest.save(dto);

		// Then
		verify(repository).save(argumentCaptor.capture());
		final NormalizedOrder captured = argumentCaptor.getValue();
		assertEquals(price, Float.valueOf(captured.getPrice()));
		assertEquals(author, captured.getAuthor());
		assertEquals(details, captured.getDetails());
		assertEquals(externalIdentifier, captured.getExternalIdentifier());
		assertNotEquals(id, captured.getId());
		assertEquals(instrument, captured.getInstrument());
		assertEquals(intent, captured.getIntent());
		assertEquals(metaType, captured.getMetaType());
		assertEquals(portfolio, captured.getPortfolio());
		assertEquals(status, captured.getStatus());
		assertEquals(settlementDate, captured.getSettlementDate());
		assertEquals(now, captured.getTimestamp());
	}

	@Test
	public void update_ok() {
		// Given
		final float price = 45.6f;
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
		final Instant settlementDate = now.plus(Duration.ofDays(1));
		final NormalizedOrderDto dto = NormalizedOrderDto.builder() //
				.price(price) //
				.author(author) //
				.details(details) //
				.externalIdentifier(externalIdentifier) //
				.id(id) //
				.instrument(instrument) //
				.intent(intent) //
				.metaType(metaType) //
				.portfolio(portfolio) //
				.status(status) //
				.settlementDate(settlementDate) //
				.timestamp(now) //
				.build();

		when(repository.findOne(ArgumentMatchers.any())).thenReturn(Optional.of(NormalizedOrder.builder().id(id).build()));
		final ArgumentCaptor<NormalizedOrder> argumentCaptor = ArgumentCaptor.forClass(NormalizedOrder.class);

		// When
		underTest.save(dto);

		// Then
		verify(repository).save(argumentCaptor.capture());
		final NormalizedOrder captured = argumentCaptor.getValue();
		assertEquals(price, Float.valueOf(captured.getPrice()));
		assertEquals(author, captured.getAuthor());
		assertEquals(details, captured.getDetails());
		assertEquals(externalIdentifier, captured.getExternalIdentifier());
		assertEquals(id, captured.getId());
		assertEquals(instrument, captured.getInstrument());
		assertEquals(intent, captured.getIntent());
		assertEquals(metaType, captured.getMetaType());
		assertEquals(portfolio, captured.getPortfolio());
		assertEquals(status, captured.getStatus());
		assertEquals(settlementDate, captured.getSettlementDate());
		assertEquals(now, captured.getTimestamp());

	}
}
