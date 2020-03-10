package org.organization.blotter.store.client;

import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import org.organization.blotter.api.model.SearchOrderCriteria;

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

	private List<FloatRangePredicateProducer> rangePredicateProducers;

	@Override
	public boolean accept(SearchOrderCriteria criteria) {
		return criteria != null && !Strings.isNullOrEmpty(criteria.getPrice());
	}

	@Override
	public List<Predicate> produce(final SearchOrderCriteria criteria, final Root<NormalizedOrder> from) {
		final String price = criteria.getPrice();
		return rangePredicateProducers.stream()
				.filter(producer -> producer.accept(price))
				.map(producer -> producer.produce(price, from.get("price")))
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
//		final List<MetaType> metaTypes = Lists.newArrayList(Splitter.on(',').split(criteria.getMetaTypes())).stream() //
//				.map(MetaType::valueOf) //
//				.collect(Collectors.toList());
//		// this must match a property in the NormalizedOrder
//		return Lists.newArrayList(from.get("metaType").in(metaTypes));
	}

}
