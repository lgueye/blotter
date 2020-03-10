package org.organization.blotter.store.client;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExactFloatRangePredicateProducer implements FloatRangePredicateProducer {
    @Override
    public boolean accept(String input) {
        try {
            Float.parseFloat(input);
            return true;
        } catch (Exception e) {
            Matcher matcher = Pattern.compile("\\[(.*?)]").matcher(input);
            boolean found = matcher.find();
            if (!found) return false;
            try {
                Float.parseFloat(matcher.group(1));
                return true;
            } catch (Exception ex) {
                return false;
            }
        }

    }

    @Override
    public List<Predicate> produce(String input, Path<Object> path) {
        return null;
    }
}
