package org.organization.blotter.api.consumer;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.organization.blotter.api.model.SearchOrderCriteria;
import org.springframework.core.convert.converter.Converter;

import java.util.Map;

/**
 * @author louis.gueye@gmail.com
 */
public class SearchOrderCriteriaToMapConverter implements Converter<SearchOrderCriteria, Map<String, String>> {
	@Override
	public Map<String, String> convert(final SearchOrderCriteria source) {
		final Map<String, String> target = Maps.newHashMap();
		if (!Strings.isNullOrEmpty(source.getMetaTypes())) {
			target.put("metaTypes", source.getMetaTypes());
		}
		if (!Strings.isNullOrEmpty(source.getAuthors())) {
			target.put("authors", source.getAuthors());
		}
		if (!Strings.isNullOrEmpty(source.getPortfolios())) {
			target.put("portfolios", source.getPortfolios());
		}
		if (!Strings.isNullOrEmpty(source.getInstruments())) {
			target.put("instruments", source.getInstruments());
		}
		if (!Strings.isNullOrEmpty(source.getIntents())) {
			target.put("intents", source.getIntents());
		}
		if (!Strings.isNullOrEmpty(source.getStatuses())) {
			target.put("statuses", source.getStatuses());
		}
		if (!Strings.isNullOrEmpty(source.getExternalIdentifiers())) {
			target.put("externalIdentifiers", source.getExternalIdentifiers());
		}
		return target;
	}
}
