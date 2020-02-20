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
		if (source.getMetaType() != null) {
			target.put("metaType", source.getMetaType().name());
		}
		if (!Strings.isNullOrEmpty(source.getAuthor())) {
			target.put("author", source.getAuthor());
		}
		if (!Strings.isNullOrEmpty(source.getPortfolio())) {
			target.put("portfolio", source.getPortfolio());
		}
		if (!Strings.isNullOrEmpty(source.getInstrument())) {
			target.put("instrument", source.getInstrument());
		}
		if (source.getIntent() != null) {
			target.put("intent", source.getIntent().name());
		}
		if (source.getStatus() != null) {
			target.put("status", source.getStatus().name());
		}
		if (!Strings.isNullOrEmpty(source.getExternalIdentifier())) {
			target.put("externalIdentifier", source.getExternalIdentifier());
		}
		return target;
	}
}
