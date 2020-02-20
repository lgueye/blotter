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
		if (source.getMetaType() != null) {
			target.add("metaType", String.format("{%s}", "metaType"));
		}
		if (!Strings.isNullOrEmpty(source.getAuthor())) {
			target.add("author", String.format("{%s}", "author"));
		}
		if (!Strings.isNullOrEmpty(source.getPortfolio())) {
			target.add("portfolio", String.format("{%s}", "portfolio"));
		}
		if (!Strings.isNullOrEmpty(source.getInstrument())) {
			target.add("instrument", String.format("{%s}", "instrument"));
		}
		if (source.getIntent() != null) {
			target.add("intent", String.format("{%s}", "intent"));
		}
		if (source.getStatus() != null) {
			target.add("status", String.format("{%s}", "status"));
		}
		if (!Strings.isNullOrEmpty(source.getExternalIdentifier())) {
			target.add("externalIdentifier", String.format("{%s}", "externalIdentifier"));
		}
		return target;
	}
}
