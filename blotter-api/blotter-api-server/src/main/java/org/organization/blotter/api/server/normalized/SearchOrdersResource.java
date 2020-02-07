package org.organization.blotter.api.server.normalized;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.organization.blotter.api.model.OrderReadDto;
import org.organization.blotter.api.model.SearchOrderCriteria;
import org.organization.blotter.store.client.BlotterStoreClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author louis.gueye@gmail.com
 */
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Slf4j
public class SearchOrdersResource {
	private final BlotterStoreClient persistenceService;

	@GetMapping
	public List<OrderReadDto> findByCriteria(final SearchOrderCriteria criteria) {
		return persistenceService.findByCriteria(criteria);
	}

}
