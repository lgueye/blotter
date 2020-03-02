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
				.metaTypes("stex") //
				.authors("any-author") //
				.portfolios("any-portfolio") //
				.instruments("any-instrument") //
				.intents("buy") //
				.externalIdentifiers("any-ext-id") //
				.statuses("validated") //
				.build();

		final Map<String, String> paramValues = Maps.newHashMap();
		paramValues.put("metaTypes", MetaType.stex.name());
		paramValues.put("authors", "any-author");
		paramValues.put("portfolios", "any-portfolio");
		paramValues.put("instruments", "any-instrument");
		paramValues.put("intents", TradeIntent.buy.name());
		paramValues.put("statuses", OrderStatus.validated.name());
		paramValues.put("externalIdentifiers", "any-ext-id");

		final MultiValueMap<String, String> paramTemplates = new LinkedMultiValueMap<>();
		paramTemplates.add("metaTypes", String.format("{%s}", "metaTypes"));
		paramTemplates.add("authors", String.format("{%s}", "authors"));
		paramTemplates.add("portfolios", String.format("{%s}", "portfolios"));
		paramTemplates.add("instruments", String.format("{%s}", "instruments"));
		paramTemplates.add("intents", String.format("{%s}", "intents"));
		paramTemplates.add("statuses", String.format("{%s}", "statuses"));
		paramTemplates.add("externalIdentifiers", String.format("{%s}", "externalIdentifiers"));

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
