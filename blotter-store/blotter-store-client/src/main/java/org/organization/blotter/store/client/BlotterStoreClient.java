package org.organization.blotter.store.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.organization.blotter.api.model.NormalizedOrderDto;
import org.organization.blotter.api.model.OrderReadDto;
import org.organization.blotter.api.model.SearchOrderCriteria;
import org.springframework.data.domain.Example;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author louis.gueye@gmail.com
 */
@RequiredArgsConstructor
@Slf4j
public class BlotterStoreClient {

	private final NormalizedOrderRepository repository;
	private final EntityManager entityManager;
	private final List<CriterionToPredicateProducer> predicateProducers;

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
		log.info("Looking for duplicate before saving {}", example);

		final Optional<NormalizedOrder> persistedOptional = repository.findOne(Example.of(example));
		if (!persistedOptional.isPresent()) {
			repository.save(order);
		} else {
			// TODO : merge
			repository.save(order.toBuilder().id(persistedOptional.get().getId()).build());
		}
	}

	public List<OrderReadDto> findByCriteria(SearchOrderCriteria criteria) {
		if (criteria == null)
			return Collections.emptyList();

		final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<NormalizedOrder> query = criteriaBuilder.createQuery(NormalizedOrder.class);
		final Root<NormalizedOrder> from = query.from(NormalizedOrder.class);
		final List<Predicate> predicates = predicateProducers.stream() //
				.filter(producer -> producer.accept(criteria)) //
				.map(producer -> producer.produce(criteria, from)) //
				.flatMap(Collection::stream) //
				.collect(Collectors.toList());
		final CriteriaQuery<NormalizedOrder> fullQuery = query.select(from).where(predicates.toArray(new Predicate[]{}));
		return entityManager.createQuery(fullQuery).getResultList().stream().map(record -> OrderReadDto.builder() //
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

		// final NormalizedOrder example = NormalizedOrder.builder().metaType(criteria.getMetaType()).portfolio(criteria.getPortfolio()).build();
		// return repository.findAll(Example.of(example)) //
		// .stream() //
		// .map(record -> OrderReadDto.builder() //
		// .amount(Float.valueOf(record.getAmount())) //
		// .author(record.getAuthor()) //
		// .externalIdentifier(record.getExternalIdentifier()) //
		// .instrument(record.getInstrument()) //
		// .intent(record.getIntent()) //
		// .metaType(record.getMetaType()) //
		// .portfolio(record.getPortfolio()) //
		// .status(record.getStatus()) //
		// .timestamp(record.getTimestamp()) //
		// .build()) //
		// .filter(Objects::nonNull) //
		// .sorted(Comparator.comparing(OrderReadDto::getTimestamp)) //
		// .collect(Collectors.toList());
	}

}
