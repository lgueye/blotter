package org.organization.blotter.api.consumer;

import lombok.RequiredArgsConstructor;
import org.organization.blotter.api.model.OrderReadDto;
import org.organization.blotter.api.model.SearchOrderCriteria;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author louis.gueye@gmail.com
 */
@RequiredArgsConstructor
public class BlotterApiConsumer {
	private final RestTemplate restTemplate;
	private final String apiUrl;
	private final SearchOrderCriteriaToMultiValueMapConverter paramTemplatesProducer;
	private final SearchOrderCriteriaToMapConverter paramValuesProducer;

	public List<OrderReadDto> findByCriteria(final SearchOrderCriteria criteria) {
		final MultiValueMap<String, String> paramTemplates = paramTemplatesProducer.convert(criteria);
		final Map<String, String> paramValues = paramValuesProducer.convert(criteria);
		final URI uri = UriComponentsBuilder //
				.fromHttpUrl(apiUrl + "/api/v1/orders") //
				.queryParams(paramTemplates) //
				.encode() //
				.buildAndExpand(Objects.requireNonNull(paramValues)) //
				.toUri();
		return restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), new ParameterizedTypeReference<List<OrderReadDto>>() {
		}).getBody();

	}
}
