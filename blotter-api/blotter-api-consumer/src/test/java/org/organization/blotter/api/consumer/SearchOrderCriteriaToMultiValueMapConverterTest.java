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
				.metaType(MetaType.stex) //
				.author("any-author") //
				.portfolio("any-portfolio") //
				.instrument("any-instrument") //
				.intent(TradeIntent.buy) //
				.externalIdentifier("any-ext-id") //
				.status(OrderStatus.validated) //
				.build();

		// When
		final MultiValueMap<String, String> actual = underTest.convert(criteria);

		// Then
		final MultiValueMap<String, String> expected = new LinkedMultiValueMap<>();
		expected.add("metaType", String.format("{%s}", "metaType"));
		expected.add("author", String.format("{%s}", "author"));
		expected.add("portfolio", String.format("{%s}", "portfolio"));
		expected.add("instrument", String.format("{%s}", "instrument"));
		expected.add("intent", String.format("{%s}", "intent"));
		expected.add("status", String.format("{%s}", "status"));
		expected.add("externalIdentifier", String.format("{%s}", "externalIdentifier"));
		assertEquals(expected, actual);
	}
}
