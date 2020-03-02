package org.organization.blotter.api.consumer;

import com.google.common.base.Strings;
import org.organization.blotter.api.model.SearchOrderCriteria;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @author louis.gueye@gmail.com
 */
public class SearchOrderCriteriaToMultiValueMapConverter implements Converter<SearchOrderCriteria, MultiValueMap<String, String>> {
	@Override
	public MultiValueMap<String, String> convert(final SearchOrderCriteria source) {
		final MultiValueMap<String, String> target = new LinkedMultiValueMap<>();
		if (!Strings.isNullOrEmpty(source.getMetaTypes())) {
			target.add("metaTypes", String.format("{%s}", "metaTypes"));
		}
		if (!Strings.isNullOrEmpty(source.getAuthors())) {
			target.add("authors", String.format("{%s}", "authors"));
		}
		if (!Strings.isNullOrEmpty(source.getPortfolios())) {
			target.add("portfolios", String.format("{%s}", "portfolios"));
		}
		if (!Strings.isNullOrEmpty(source.getInstruments())) {
			target.add("instruments", String.format("{%s}", "instruments"));
		}
		if (!Strings.isNullOrEmpty(source.getIntents())) {
			target.add("intents", String.format("{%s}", "intents"));
		}
		if (!Strings.isNullOrEmpty(source.getStatuses())) {
			target.add("statuses", String.format("{%s}", "statuses"));
		}
		if (!Strings.isNullOrEmpty(source.getExternalIdentifiers())) {
			target.add("externalIdentifiers", String.format("{%s}", "externalIdentifiers"));
		}
		return target;
	}
}
