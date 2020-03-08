package org.organization.blotter.store.client;

import com.google.common.base.Joiner;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.organization.blotter.api.model.SearchOrderCriteria;
import org.organization.blotter.shared.model.MetaType;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * @author louis.gueye@gmail.com
 */
public class MetaTypesCriteriaToPredicateProducerTest {
	private MetaTypesCriteriaToPredicateProducer underTest;

	@Before
	public void before() {
		underTest = new MetaTypesCriteriaToPredicateProducer();
	}

	@Test
	public void accept_ok() {
		// Given
		final SearchOrderCriteria criteria = SearchOrderCriteria.builder().metaTypes("foo").build();

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
		criteria = SearchOrderCriteria.builder().portfolios("foo").instruments("bar").build();

		// When
		actual = underTest.accept(criteria);

		// Then
		assertFalse(actual);
	}

	@Test
	public void produce_ok() {
		// Given
		final List<MetaType> metaTypes = Lists.newArrayList(MetaType.stex, MetaType.fx);
		final SearchOrderCriteria criteria = SearchOrderCriteria.builder() //
				.metaTypes(Joiner.on(',').join(metaTypes.stream().map(Enum::name).collect(Collectors.toList()))) //
				.build();
		final Root<NormalizedOrder> from = mock(Root.class);
		final Path path = mock(Path.class);
		when(from.get("metaType")).thenReturn(path);

		// When
		underTest.produce(criteria, from);

		// Then
		verify(path).in(metaTypes);
	}

}