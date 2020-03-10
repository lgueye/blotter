package org.organization.blotter.store.client.parsers.range;

import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import org.organization.blotter.api.model.SearchOrderCriteria;
import org.organization.blotter.store.client.NormalizedOrder;
import org.organization.blotter.store.client.parsers.CriterionToPredicateProducer;
import org.organization.blotter.store.client.parsers.range.numeric.FloatRangePredicateProducer;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author louis.gueye@gmail.com
 */
@RequiredArgsConstructor
public class PriceCriteriaToPredicateProducer implements CriterionToPredicateProducer {

	private final List<FloatRangePredicateProducer> rangePredicateProducers;

	@Override
	public boolean accept(SearchOrderCriteria criteria) {
		return criteria != null && !Strings.isNullOrEmpty(criteria.getPrice()) && !Strings.isNullOrEmpty(criteria.getPrice().trim());
	}

	@Override
	public List<Predicate> produce(final SearchOrderCriteria criteria, final Root<NormalizedOrder> from, final CriteriaBuilder criteriaBuilder) {
		final String priceCriteria = criteria.getPrice();
		return rangePredicateProducers.stream()
				.filter(producer -> producer.accept(priceCriteria))
				.map(producer -> producer.produce(priceCriteria, from, "price", criteriaBuilder))
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
	}

}
