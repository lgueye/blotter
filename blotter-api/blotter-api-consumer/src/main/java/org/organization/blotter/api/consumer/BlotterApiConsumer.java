package org.organization.blotter.api.consumer;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import org.organization.blotter.api.model.OrderReadDto;
import org.organization.blotter.api.model.SearchOrderCriteria;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * @author louis.gueye@gmail.com
 */
@RequiredArgsConstructor
public class BlotterApiConsumer {
	private final RestTemplate restTemplate;
	private final String apiUrl;

	public List<OrderReadDto> findByCriteria(final SearchOrderCriteria criteria) {
		final MultiValueMap<String, String> paramTemplates = new LinkedMultiValueMap<>();
		final Map<String, String> paramValues = Maps.newHashMap();
		if (criteria.getMetaType() != null) {
			paramTemplates.add("metaType", String.format("{%s}", "metaType"));
			paramValues.put("metaType", criteria.getMetaType().name());
		}
		if (!Strings.isNullOrEmpty(criteria.getAuthor())) {
			paramTemplates.add("author", String.format("{%s}", "author"));
			paramValues.put("author", criteria.getAuthor());
		}
		if (!Strings.isNullOrEmpty(criteria.getPortfolio())) {
			paramTemplates.add("portfolio", String.format("{%s}", "portfolio"));
			paramValues.put("portfolio", criteria.getPortfolio());
		}
		if (!Strings.isNullOrEmpty(criteria.getInstrument())) {
			paramTemplates.add("instrument", String.format("{%s}", "instrument"));
			paramValues.put("instrument", criteria.getPortfolio());
		}
		if (criteria.getIntent() != null) {
			paramTemplates.add("intent", String.format("{%s}", "intent"));
			paramValues.put("intent", criteria.getIntent().name());
		}
		if (!Strings.isNullOrEmpty(criteria.getExternalIdentifier())) {
			paramTemplates.add("externalIdentifier", String.format("{%s}", "externalIdentifier"));
			paramValues.put("externalIdentifier", criteria.getExternalIdentifier());
		}

		final URI uri = UriComponentsBuilder.fromHttpUrl(apiUrl + "/api/v1/orders").queryParams(paramTemplates).encode().buildAndExpand(paramValues)
				.toUri();
		return restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), new ParameterizedTypeReference<List<OrderReadDto>>() {
		}).getBody();

	}
}
