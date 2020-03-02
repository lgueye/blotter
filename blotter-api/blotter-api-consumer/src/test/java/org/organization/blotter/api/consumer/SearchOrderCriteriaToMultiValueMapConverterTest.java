package org.organization.blotter.api.consumer;

import org.junit.Before;
import org.junit.Test;
import org.organization.blotter.api.model.SearchOrderCriteria;
import org.organization.blotter.shared.model.MetaType;
import org.organization.blotter.shared.model.OrderStatus;
import org.organization.blotter.shared.model.TradeIntent;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author louis.gueye@gmail.com
 */
public class SearchOrderCriteriaToMultiValueMapConverterTest {
	private SearchOrderCriteriaToMultiValueMapConverter underTest;

	@Before
	public void before() {
		underTest = new SearchOrderCriteriaToMultiValueMapConverter();
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
				.build();

		// When
		final MultiValueMap<String, String> actual = underTest.convert(criteria);

		// Then
		final MultiValueMap<String, String> expected = new LinkedMultiValueMap<>();
		expected.add("metaTypes", String.format("{%s}", "metaTypes"));
		expected.add("authors", String.format("{%s}", "authors"));
		expected.add("portfolios", String.format("{%s}", "portfolios"));
		expected.add("instruments", String.format("{%s}", "instruments"));
		expected.add("intents", String.format("{%s}", "intents"));
		expected.add("statuses", String.format("{%s}", "statuses"));
		expected.add("externalIdentifiers", String.format("{%s}", "externalIdentifiers"));
		assertEquals(expected, actual);
	}
}
