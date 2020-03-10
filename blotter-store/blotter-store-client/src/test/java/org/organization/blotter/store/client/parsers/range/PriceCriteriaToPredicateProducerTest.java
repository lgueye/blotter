package org.organization.blotter.store.client.parsers.range;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.organization.blotter.api.model.SearchOrderCriteria;

import static org.junit.jupiter.api.Assertions.*;

public class PriceCriteriaToPredicateProducerTest {
	private PriceCriteriaToPredicateProducer underTest;

	@Before
	public void before() {
		underTest = new PriceCriteriaToPredicateProducer(Lists.newArrayList());
	}

	@Test
	public void accept_ok() {
		// Given
		final SearchOrderCriteria criteria = SearchOrderCriteria.builder().price("[150000,200000]").build();

		// When
		final boolean actual = underTest.accept(criteria);

		// Then
		assertTrue(actual);
	}

	@Test
	public void accept_ko() {
		assertFalse(underTest.accept(SearchOrderCriteria.builder().portfolios("foo,bar,baz").build()));
		assertFalse(underTest.accept(SearchOrderCriteria.builder().price("   ").build()));
	}
}