package org.organization.blotter.store.client;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.modelmapper.ModelMapper;
import org.organization.blotter.api.model.NormalizedOrderDto;
import org.organization.blotter.api.model.OrderReadDto;
import org.organization.blotter.api.model.SearchOrderCriteria;
import org.organization.blotter.store.client.model.tables.NormalizedOrders;
import org.organization.blotter.store.client.model.tables.records.NormalizedOrdersRecord;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author louis.gueye@gmail.com
 */
@RequiredArgsConstructor
public class BlotterStoreClient {

	private final DSLContext dslContext;
	private final ModelMapper modelMapper;

	// private final Map<String, NormalizedOrderDto> normalizedStore = Maps.newConcurrentMap();

	public void save(NormalizedOrderDto dto) {
		// if (Strings.isNullOrEmpty(dto.getId())) {
		// return;
		// }
		final int count = dslContext
				.selectCount()
				.from(NormalizedOrders.NORMALIZED_ORDERS)
				.where(NormalizedOrders.NORMALIZED_ORDERS.PORTFOLIO.eq(dto.getPortfolio())
						.and(NormalizedOrders.NORMALIZED_ORDERS.INSTRUMENT.eq(dto.getInstrument()))
						.and(NormalizedOrders.NORMALIZED_ORDERS.META_TYPE.eq(dto.getMetaType().name()))
						.and(NormalizedOrders.NORMALIZED_ORDERS.INTENT.eq(dto.getIntent().name()))).fetchOne(0, int.class);

		if (count == 0) {
			dslContext
					.insertInto(NormalizedOrders.NORMALIZED_ORDERS)
					.columns(NormalizedOrders.NORMALIZED_ORDERS.ID, NormalizedOrders.NORMALIZED_ORDERS.EXTERNAL_IDENTIFIER,
							NormalizedOrders.NORMALIZED_ORDERS.AUTHOR, NormalizedOrders.NORMALIZED_ORDERS.INSTRUMENT,
							NormalizedOrders.NORMALIZED_ORDERS.PORTFOLIO, NormalizedOrders.NORMALIZED_ORDERS.META_TYPE,
							NormalizedOrders.NORMALIZED_ORDERS.INTENT, NormalizedOrders.NORMALIZED_ORDERS.DETAILS,
							NormalizedOrders.NORMALIZED_ORDERS.STATUS, NormalizedOrders.NORMALIZED_ORDERS.TIMESTAMP)
					.values(UUID.randomUUID().toString(), dto.getExternalIdentifier(), dto.getAuthor(), dto.getInstrument(), dto.getPortfolio(),
							dto.getMetaType().name(), dto.getIntent().name(), dto.getStatus().name(), dto.getDetails(), dto.getTimestamp().toString())
					.execute();
			// normalizedStore.put(UUID.randomUUID().toString(), dto);
		}
	}

	public List<OrderReadDto> findByCriteria(SearchOrderCriteria criteria) {
		if (criteria == null)
			return Collections.emptyList();
		NormalizedOrdersRecord example = new NormalizedOrdersRecord();
		example.setPortfolio(criteria.getPortfolio());
		example.setMetaType(criteria.getMetaType().name());
		return dslContext.selectFrom(NormalizedOrders.NORMALIZED_ORDERS).where(DSL.condition(example))//
				.fetch() //
				.stream() //
				.map(record -> modelMapper.map(record, OrderReadDto.class)) //
				.sorted(Comparator.comparing(OrderReadDto::getTimestamp)) //
				.collect(Collectors.toList());

		// final Collection<NormalizedOrderDto> orders = normalizedStore.values();
		// return orders
		// .stream()
		// //
		// .filter(order -> !Strings.isNullOrEmpty(criteria.getPortfolio()) && criteria.getPortfolio().equals(order.getPortfolio())) //
		// .filter(order -> criteria.getMetaType() != null && criteria.getMetaType().equals(order.getMetaType())) //
		// .map(normalized -> OrderReadDto.builder() //
		// .amount(normalized.getAmount()) //
		// .author(normalized.getAuthor()) //
		// .externalIdentifier(normalized.getExternalIdentifier()) //
		// .instrument(normalized.getInstrument()) //
		// .intent(normalized.getIntent()) //
		// .metaType(normalized.getMetaType()) //
		// .portfolio(normalized.getPortfolio()) //
		// .status(normalized.getStatus()) //
		// .timestamp(normalized.getTimestamp()) //
		// .build()) //
		// .sorted(Comparator.comparing(OrderReadDto::getTimestamp)) //
		// .collect(Collectors.toList());

	}

}
