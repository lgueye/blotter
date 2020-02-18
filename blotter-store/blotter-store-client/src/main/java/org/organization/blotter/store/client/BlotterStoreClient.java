package org.organization.blotter.store.client;

import lombok.RequiredArgsConstructor;
import org.organization.blotter.api.model.NormalizedOrderDto;
import org.organization.blotter.api.model.OrderReadDto;
import org.organization.blotter.api.model.SearchOrderCriteria;
import org.springframework.data.domain.Example;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author louis.gueye@gmail.com
 */
@RequiredArgsConstructor
public class BlotterStoreClient {

	private final NormalizedOrderRepository repository;

	public void save(NormalizedOrderDto dto) {
		final NormalizedOrder order = NormalizedOrder.builder() //
				.amount(dto.getAmount().toString()) //
				.author(dto.getAuthor()) //
				.details(dto.getDetails()) //
				.externalIdentifier(dto.getExternalIdentifier()) //
				.instrument(dto.getInstrument()) //
				.intent(dto.getIntent()).metaType(dto.getMetaType()) //
				.id(UUID.randomUUID().toString()) //
				.portfolio(dto.getPortfolio()) //
				.status(dto.getStatus()) //
				.timestamp(dto.getTimestamp()) //
				.build();
		final NormalizedOrder example = NormalizedOrder.builder() //
				.instrument(dto.getInstrument()) //
				.intent(dto.getIntent()) //
				.metaType(dto.getMetaType()) //
				.portfolio(dto.getPortfolio()) //
				.status(dto.getStatus()) //
				.build();

		final Optional<NormalizedOrder> persistedOptional = repository.findOne(Example.of(example));
		if (!persistedOptional.isPresent()) {
			repository.save(order);
		} else {
			repository.save(order.toBuilder().id(persistedOptional.get().getId()).build());
		}
	}

	public List<OrderReadDto> findByCriteria(SearchOrderCriteria criteria) {
		if (criteria == null)
			return Collections.emptyList();

		final NormalizedOrder example = NormalizedOrder.builder().metaType(criteria.getMetaType()).portfolio(criteria.getPortfolio()).build();
		return repository.findAll(Example.of(example)) //
				.stream() //
				.map(record -> OrderReadDto.builder() //
						.amount(Float.valueOf(record.getAmount())) //
						.author(record.getAuthor()) //
						.externalIdentifier(record.getExternalIdentifier()) //
						.instrument(record.getInstrument()) //
						.intent(record.getIntent()) //
						.metaType(record.getMetaType()) //
						.portfolio(record.getPortfolio()) //
						.status(record.getStatus()) //
						.timestamp(record.getTimestamp()) //
						.build()) //
				.filter(Objects::nonNull) //
				.sorted(Comparator.comparing(OrderReadDto::getTimestamp)) //
				.collect(Collectors.toList());
	}

}
