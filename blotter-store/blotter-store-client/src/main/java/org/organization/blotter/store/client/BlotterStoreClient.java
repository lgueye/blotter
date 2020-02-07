package org.organization.blotter.store.client;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.organization.blotter.api.model.NormalizedOrderDto;
import org.organization.blotter.api.model.OrderReadDto;
import org.organization.blotter.api.model.SearchOrderCriteria;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author louis.gueye@gmail.com
 */
public class BlotterStoreClient {
	private final Map<String, NormalizedOrderDto> normalizedStore = Maps.newConcurrentMap();

	public void save(NormalizedOrderDto dto) {
		if (!normalizedStore.values().contains(dto)) {
			normalizedStore.put(UUID.randomUUID().toString(), dto);
		} else {
			normalizedStore.put(dto.getId(), dto);
		}
	}

	public List<OrderReadDto> findByCriteria(SearchOrderCriteria criteria) {
		final Collection<NormalizedOrderDto> orders = normalizedStore.values();
		return orders
				.stream()
				.filter(order -> criteria != null && !Strings.isNullOrEmpty(criteria.getPortfolio())
						&& criteria.getPortfolio().equals(order.getPortfolio()))
				.filter(order -> criteria.getMetaType() != null && criteria.getMetaType().equals(order.getMetaType()))
				.map(normalized -> OrderReadDto.builder() //
						.amount(normalized.getAmount()) //
						.author(normalized.getAuthor()) //
						.externalIdentifier(normalized.getExternalIdentifier()) //
						.instrument(normalized.getInstrument()) //
						.intent(normalized.getIntent()) //
						.metaType(normalized.getMetaType()) //
						.portfolio(normalized.getPortfolio()) //
						.timestamp(normalized.getTimestamp()) //
						.build()) //
				.sorted(Comparator.comparing(OrderReadDto::getTimestamp)).collect(Collectors.toList());

	}

}
