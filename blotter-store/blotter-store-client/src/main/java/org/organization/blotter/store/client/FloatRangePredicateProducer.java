package org.organization.blotter.store.client;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.List;

public interface FloatRangePredicateProducer {
    boolean accept(String input);

    List<Predicate> produce(String input, Path<Object> path);
}
