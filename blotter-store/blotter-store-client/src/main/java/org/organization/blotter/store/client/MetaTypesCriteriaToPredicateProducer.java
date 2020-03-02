package org.organization.blotter.store.client;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.organization.blotter.api.model.SearchOrderCriteria;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author louis.gueye@gmail.com
 */
@RequiredArgsConstructor
public class MetaTypesCriteriaToPredicateProducer implements CriterionToPredicateProducer {

	@Override
	public boolean accept(SearchOrderCriteria criteria) {
		return criteria != null && !Strings.isNullOrEmpty(criteria.getMetaTypes());
	}

	@Override
	public List<Predicate> produce(final SearchOrderCriteria criteria, final Root<NormalizedOrder> from) {
		final List<String> metaTypes = Lists.newArrayList(Splitter.on(',').split(criteria.getMetaTypes()));
		// this must match a property in the NormalizedOrder
		return Lists.newArrayList(from.get("metaType").in(metaTypes));
	}

}
