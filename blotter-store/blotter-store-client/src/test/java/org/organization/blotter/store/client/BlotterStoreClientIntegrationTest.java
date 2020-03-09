package org.organization.blotter.store.client;

import com.google.common.collect.Lists;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.organization.blotter.api.model.OrderReadDto;
import org.organization.blotter.api.model.SearchOrderCriteria;
import org.organization.blotter.shared.model.MetaType;
import org.organization.blotter.shared.model.OrderStatus;
import org.organization.blotter.shared.model.TradeIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author louis.gueye@gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BlotterStoreClientConfiguration.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class})
@Transactional
public class BlotterStoreClientIntegrationTest {

	@Autowired
	private BlotterStoreClient underTest;

	@Test
	public void find_by_criteria_empty_results() {
		assertEquals(Collections.emptyList(), underTest.findByCriteria(null));
	}

	@Ignore
	@Test
	public void find_by_criteria_ok() {
		// Given

		final SearchOrderCriteria criteria = SearchOrderCriteria.builder().portfolios("pf-001").metaTypes("stex").build();
		final float o1Price = 45.6f;
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
				.price(String.valueOf(o1Price)) //
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
		final float o2Price = 88.2f;
		final NormalizedOrder o2 = NormalizedOrder.builder() //
				.price(String.valueOf(o2Price)) //
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

		// When
		final List<OrderReadDto> actual = underTest.findByCriteria(criteria);

		final OrderReadDto r1 = OrderReadDto.builder() //
				.price(o1Price) //
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
				.price(o2Price) //
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
	}
}
