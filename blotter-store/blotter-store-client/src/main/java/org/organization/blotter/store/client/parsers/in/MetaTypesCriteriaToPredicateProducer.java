package org.organization.blotter.store.client.parsers.in;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.organization.blotter.api.model.SearchOrderCriteria;
import org.organization.blotter.shared.model.MetaType;
import org.organization.blotter.store.client.NormalizedOrder;
import org.organization.blotter.store.client.parsers.CriterionToPredicateProducer;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Responsible to handle metatypes criteria
 * Will accept if  metatypes are provided as criteria
 * Will ignore any unsupported metatype in query
 * Will ignore any unsupported metatype in query
 *
 * @author louis.gueye@gmail.com
 */
@RequiredArgsConstructor
public class MetaTypesCriteriaToPredicateProducer implements CriterionToPredicateProducer {

    @Override
    public boolean accept(SearchOrderCriteria criteria) {
        return criteria != null && !Strings.isNullOrEmpty(criteria.getMetaTypes()) && Lists.newArrayList(Splitter.on(',').split(criteria.getMetaTypes())).stream() //
				.map(token -> {
					try {
						return MetaType.valueOf(token);
					} catch (Exception e) {
						return null;
					}
				}) //
				.filter(Objects::nonNull).count() > 0;
    }

    @Override
    public List<Predicate> produce(final SearchOrderCriteria criteria, final Root<NormalizedOrder> from, final CriteriaBuilder criteriaBuilder) {
        final List<MetaType> metaTypes = Lists.newArrayList(Splitter.on(',').split(criteria.getMetaTypes())).stream() //
                .map(token -> {
                    try {
                        return MetaType.valueOf(token);
                    } catch (Exception e) {
                        return null;
                    }
                }) //
                .filter(Objects::nonNull) //
                .collect(Collectors.toList());
        // this must match a property in the NormalizedOrder
        return Lists.newArrayList(from.get("metaType").in(metaTypes));
    }

}
