package org.organization.blotter.store.client.parsers.range.numeric;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.organization.blotter.store.client.NormalizedOrder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BetweenExclusiveExclusiveFloatRangePredicateProducer implements FloatRangePredicateProducer {

	private static final Pattern PATTERN = Pattern.compile("](.*?)\\[");

	@Override
	public boolean accept(String input) {
		final Matcher matcher = PATTERN.matcher(input);
		boolean found = matcher.find();
		if (!found)
			return false;
		try {
			final List<Float> range = Lists.newArrayList(Splitter.on(',').trimResults().omitEmptyStrings().split(matcher.group(1))).stream()
					.map(Float::parseFloat).collect(Collectors.toList());
			return range.size() == 2 && range.get(0) < range.get(1);
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public List<Predicate> produce(String input, final Root<NormalizedOrder> from, final String path, final CriteriaBuilder criteriaBuilder) {
		final Matcher matcher = PATTERN.matcher(input);
		final List<Float> range = Lists.newArrayList(Splitter.on(',').trimResults().omitEmptyStrings().split(matcher.group(1))).stream()
				.map(Float::parseFloat).collect(Collectors.toList());
		return Lists.newArrayList(criteriaBuilder.greaterThan(from.get(path), range.get(0)), criteriaBuilder.lessThan(from.get(path), range.get(1)));
	}
}
