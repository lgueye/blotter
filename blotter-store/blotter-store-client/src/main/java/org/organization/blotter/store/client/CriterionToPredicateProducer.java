package org.organization.blotter.store.client;

import org.organization.blotter.api.model.SearchOrderCriteria;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author louis.gueye@gmail.com
 */
public interface CriterionToPredicateProducer {
	boolean accept(final SearchOrderCriteria criteria);
	List<Predicate> produce(final SearchOrderCriteria criteria, final Root<NormalizedOrder> from);
}
