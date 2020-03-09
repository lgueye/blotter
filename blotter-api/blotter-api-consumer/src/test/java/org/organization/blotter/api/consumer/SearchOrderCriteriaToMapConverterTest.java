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
				.metaTypes("stex") //
				.authors("any-author") //
				.portfolios("any-portfolio") //
				.instruments("any-instrument") //
				.intents("buy") //
				.externalIdentifiers("any-ext-id") //
				.statuses("validated") //
				.price("[600,800]") //
				.settlementDate("[2020-03-15T00:00:00.000Z,2020-03-17T00:00:00.000Z]") //
				.build();

		// When
		final Map<String, String> actual = underTest.convert(criteria);

		// Then
		final Map<String, String> expected = Maps.newHashMap();
		expected.put("metaTypes", MetaType.stex.name());
		expected.put("authors", "any-author");
		expected.put("portfolios", "any-portfolio");
		expected.put("instruments", "any-instrument");
		expected.put("intents", TradeIntent.buy.name());
		expected.put("statuses", OrderStatus.validated.name());
		expected.put("externalIdentifiers", "any-ext-id");
		expected.put("price", "[600,800]");
		expected.put("settlementDate", "[2020-03-15T00:00:00.000Z,2020-03-17T00:00:00.000Z]");
		assertEquals(expected, actual);

	}
}
