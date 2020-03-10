package org.organization.blotter.store.client.parsers.range.numeric;

import org.organization.blotter.store.client.NormalizedOrder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public interface FloatRangePredicateProducer {
	boolean accept(String input);
	List<Predicate> produce(String input, final Root<NormalizedOrder> from, final String path, final CriteriaBuilder criteriaBuilder);
}
