package org.organization.blotter.api.consumer;

import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;
import org.organization.blotter.api.model.SearchOrderCriteria;
import org.organization.blotter.shared.model.MetaType;
import org.organization.blotter.shared.model.OrderStatus;
import org.organization.blotter.shared.model.TradeIntent;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author louis.gueye@gmail.com
 */
public class SearchOrderCriteriaToMapConverterTest {
	private SearchOrderCriteriaToMapConverter underTest;

	@Before
	public void before() {
		underTest = new SearchOrderCriteriaToMapConverter();
	}

	@Test
	public void convert_ok() {
		// Given
		final SearchOrderCriteria criteria = SearchOrderCriteria.builder() //
				.metaType(MetaType.stex) //
				.author("any-author") //
				.portfolio("any-portfolio") //
				.instrument("any-instrument") //
				.intent(TradeIntent.buy) //
				.externalIdentifier("any-ext-id") //
				.status(OrderStatus.validated) //
				.build();

		// When
		final Map<String, String> actual = underTest.convert(criteria);

		// Then
		final Map<String, String> expected = Maps.newHashMap();
		expected.put("metaType", MetaType.stex.name());
		expected.put("author", "any-author");
		expected.put("portfolio", "any-portfolio");
		expected.put("instrument", "any-instrument");
		expected.put("intent", TradeIntent.buy.name());
		expected.put("status", OrderStatus.validated.name());
		expected.put("externalIdentifier", "any-ext-id");
		assertEquals(expected, actual);

	}
}
