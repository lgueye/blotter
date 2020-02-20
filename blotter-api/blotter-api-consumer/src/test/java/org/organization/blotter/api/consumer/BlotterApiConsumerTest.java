package org.organization.blotter.api.consumer;

import com.google.common.collect.Maps;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.organization.blotter.api.model.OrderReadDto;
import org.organization.blotter.api.model.SearchOrderCriteria;
import org.organization.blotter.shared.model.MetaType;
import org.organization.blotter.shared.model.OrderStatus;
import org.organization.blotter.shared.model.TradeIntent;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author louis.gueye@gmail.com
 */
public class BlotterApiConsumerTest {
	private BlotterApiConsumer underTest;
	private RestTemplate restTemplate;
	private String apiUrl;

	@Before
	public void before() {
		restTemplate = mock(RestTemplate.class);
		apiUrl = "http://foo.bar";
		underTest = new BlotterApiConsumer(restTemplate, apiUrl, new SearchOrderCriteriaToMultiValueMapConverter(),
				new SearchOrderCriteriaToMapConverter());
	}

	@Test
	public void findByCriteria() {
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

		final Map<String, String> paramValues = Maps.newHashMap();
		paramValues.put("metaType", MetaType.stex.name());
		paramValues.put("author", "any-author");
		paramValues.put("portfolio", "any-portfolio");
		paramValues.put("instrument", "any-instrument");
		paramValues.put("intent", TradeIntent.buy.name());
		paramValues.put("status", OrderStatus.validated.name());
		paramValues.put("externalIdentifier", "any-ext-id");

		final MultiValueMap<String, String> paramTemplates = new LinkedMultiValueMap<>();
		paramTemplates.add("metaType", String.format("{%s}", "metaType"));
		paramTemplates.add("author", String.format("{%s}", "author"));
		paramTemplates.add("portfolio", String.format("{%s}", "portfolio"));
		paramTemplates.add("instrument", String.format("{%s}", "instrument"));
		paramTemplates.add("intent", String.format("{%s}", "intent"));
		paramTemplates.add("status", String.format("{%s}", "status"));
		paramTemplates.add("externalIdentifier", String.format("{%s}", "externalIdentifier"));

		final URI uri = UriComponentsBuilder //
				.fromHttpUrl(apiUrl + "/api/v1/orders") //
				.queryParams(paramTemplates) //
				.encode() //
				.buildAndExpand(Objects.requireNonNull(paramValues)) //
				.toUri();

		final List<OrderReadDto> expected = Lists.newArrayList(OrderReadDto.builder().build(), OrderReadDto.builder().build());
		when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), any(HttpEntity.class), any(ParameterizedTypeReference.class))).thenReturn(
				new ResponseEntity<>(expected, HttpStatus.OK));

		// When
		final List<OrderReadDto> actual = underTest.findByCriteria(criteria);

		// Then
		assertSame(expected, actual);
	}
}
