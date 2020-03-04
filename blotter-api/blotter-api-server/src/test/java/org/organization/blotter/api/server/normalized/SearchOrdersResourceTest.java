package org.organization.blotter.api.server.normalized;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.organization.blotter.api.model.OrderReadDto;
import org.organization.blotter.api.model.SearchOrderCriteria;
import org.organization.blotter.shared.configuration.BlotterSharedSerializationConfiguration;
import org.organization.blotter.shared.model.OrderStatus;
import org.organization.blotter.store.client.BlotterStoreClient;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author louis.gueye@gmail.com
 */
@RunWith(MockitoJUnitRunner.class)
public class SearchOrdersResourceTest {
	private final String API_URL = "http://foo.bar";
	@Mock
	private BlotterStoreClient service;
	private ObjectMapper objectMapper;
	private MockMvc mockMvc;

	@Before
	public void before() {
		SearchOrdersResource underTest = new SearchOrdersResource(service);
		objectMapper = new BlotterSharedSerializationConfiguration().objectMapper();
		mockMvc = MockMvcBuilders.standaloneSetup(underTest).setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper)).build();
	}

	@Test
	public void findByCriteria() throws Exception {
		// Given
		final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		final String portfolios = "pf-001,pf-002";
		final String instruments = "inst-0005,inst-0007";
		final String statuses = "placed,validated";
		final String authors = "louis,alban";
		params.add("portfolios", portfolios);
		params.add("instruments", instruments);
		params.add("statuses", statuses);
		params.add("authors", authors);
		final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(UriComponentsBuilder.fromHttpUrl(API_URL)
				.path("/api/v1/orders").queryParams(params).build().toUri());
		final List<OrderReadDto> expected = Lists.newArrayList(
				OrderReadDto.builder().portfolio("pf-001").instrument("inst-0005").status(OrderStatus.validated).author("louis").build(),
				OrderReadDto.builder().portfolio("pf-002").instrument("inst-0007").status(OrderStatus.placed).author("alban").build());
		final SearchOrderCriteria criteria = SearchOrderCriteria.builder().portfolios(portfolios).instruments(instruments).statuses(statuses)
				.authors(authors).build();
		when(service.findByCriteria(criteria)).thenReturn(expected);

		// When
		mockMvc.perform(requestBuilder) //
				.andExpect(MockMvcResultMatchers.status().isOk()) //
				.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expected)));

		verify(service).findByCriteria(criteria);
	}

}
