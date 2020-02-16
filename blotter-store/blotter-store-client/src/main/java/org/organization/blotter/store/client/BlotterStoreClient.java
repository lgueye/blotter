package org.organization.blotter.store.client;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.organization.blotter.api.model.NormalizedOrderDto;
import org.organization.blotter.api.model.OrderReadDto;
import org.organization.blotter.api.model.SearchOrderCriteria;
import org.organization.blotter.shared.model.MetaType;
import org.organization.blotter.shared.model.OrderStatus;
import org.organization.blotter.shared.model.TradeIntent;
import org.organization.blotter.store.client.model.tables.NormalizedOrders;
import org.organization.blotter.store.client.model.tables.records.NormalizedOrdersRecord;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author louis.gueye@gmail.com
 */
@RequiredArgsConstructor
public class BlotterStoreClient {

	private final DSLContext dslContext;

	public void save(NormalizedOrderDto dto) {
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
							NormalizedOrders.NORMALIZED_ORDERS.PORTFOLIO, NormalizedOrders.NORMALIZED_ORDERS.AMOUNT,
							NormalizedOrders.NORMALIZED_ORDERS.META_TYPE, NormalizedOrders.NORMALIZED_ORDERS.INTENT,
							NormalizedOrders.NORMALIZED_ORDERS.STATUS, NormalizedOrders.NORMALIZED_ORDERS.DETAILS,
							NormalizedOrders.NORMALIZED_ORDERS.TIMESTAMP)
					.values(UUID.randomUUID().toString(), dto.getExternalIdentifier(), dto.getAuthor(), dto.getInstrument(), dto.getPortfolio(),
							dto.getAmount().toString(), dto.getMetaType().name(), dto.getIntent().name(), dto.getStatus().name(), dto.getDetails(),
							dto.getTimestamp().toString()).execute();
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
				.map(record -> OrderReadDto.builder() //
						.amount(Float.valueOf(record.getAmount())) //
						.author(record.getAuthor()) //
						.externalIdentifier(record.getExternalIdentifier()) //
						.instrument(record.getInstrument()) //
						.intent(TradeIntent.valueOf(record.getIntent())) //
						.metaType(MetaType.valueOf(record.getMetaType())) //
						.portfolio(record.getPortfolio()) //
						.status(OrderStatus.valueOf(record.getStatus())) //
						.timestamp(Instant.parse(record.getTimestamp())) //
						.build()) //
				.filter(Objects::nonNull).sorted(Comparator.comparing(OrderReadDto::getTimestamp)) //
				.collect(Collectors.toList());
	}

}
