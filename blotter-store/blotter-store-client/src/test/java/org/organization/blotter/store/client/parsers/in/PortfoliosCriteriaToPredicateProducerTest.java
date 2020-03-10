package org.organization.blotter.store.client.parsers.in;

import com.google.common.base.Joiner;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.organization.blotter.api.model.SearchOrderCriteria;
import org.organization.blotter.store.client.NormalizedOrder;
import org.organization.blotter.store.client.parsers.in.PortfoliosCriteriaToPredicateProducer;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * @author louis.gueye@gmail.com
 */
public class PortfoliosCriteriaToPredicateProducerTest {
	private PortfoliosCriteriaToPredicateProducer underTest;

	@Before
	public void before() {
		underTest = new PortfoliosCriteriaToPredicateProducer();
	}

	@Test
	public void accept_ok() {
		// Given
		final SearchOrderCriteria criteria = SearchOrderCriteria.builder().portfolios("foo").build();

		// When
		final boolean actual = underTest.accept(criteria);

		// Then
		assertTrue(actual);
	}

	@Test
	public void accept_ko() {
		SearchOrderCriteria criteria = null;
		boolean actual;

		// When
		actual = underTest.accept(criteria);

		// Then
		assertFalse(actual);

		// Given
		criteria = SearchOrderCriteria.builder().metaTypes("foo").instruments("bar").build();

		// When
		actual = underTest.accept(criteria);

		// Then
		assertFalse(actual);
	}

	@Test
	public void produce_ok() {
		// Given
		final List<String> portfolios = Lists.newArrayList("pf-001", "pf-002");
		final SearchOrderCriteria criteria = SearchOrderCriteria.builder() //
				.portfolios(Joiner.on(',').join(portfolios)) //
				.build();
		final Root<NormalizedOrder> from = mock(Root.class);
		final Path path = mock(Path.class);
		when(from.get("portfolio")).thenReturn(path);

		// When
		underTest.produce(criteria, from, mock(CriteriaBuilder.class));

		// Then
		verify(path).in(portfolios);
	}

}
