package org.organization.blotter.api.consumer;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author louis.gueye@gmail.com
 */
@RequiredArgsConstructor
public class BlotterApiConsumer {
	private final RestTemplate restTempplate;
	private final String apiUrl;
	public List<OrderReadDto> findByCriteria(final List<SearchOrderCriteria> criteria) {
		return Lists.newArrayList();
	}
}
