package org.organization.blotter.store.client.parsers.range.numeric;

import com.google.common.collect.Lists;
import org.organization.blotter.store.client.NormalizedOrder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ToFloatRangePredicateProducer implements FloatRangePredicateProducer {

	private static final Pattern PATTERN = Pattern.compile("](.*?)]");

	@Override
	public boolean accept(String input) {
		final Matcher matcher = PATTERN.matcher(input);
		boolean found = matcher.find();
		if (!found)
			return false;
		try {
			Float.parseFloat(matcher.group(1));
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public List<Predicate> produce(String input, final Root<NormalizedOrder> from, final String path, final CriteriaBuilder criteriaBuilder) {
		final Matcher matcher = PATTERN.matcher(input);
		final float value = Float.parseFloat(matcher.group(1));
		return Lists.newArrayList(criteriaBuilder.equal(from.get(path), value));
	}
}
