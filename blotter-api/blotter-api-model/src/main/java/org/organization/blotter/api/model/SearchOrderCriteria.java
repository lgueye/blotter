package org.organization.blotter.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author louis.gueye@gmail.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchOrderCriteria {
	private String externalIdentifiers;
	private String metaTypes;
	private String authors;
	private String portfolios;
	private String price;
	private String intents;
	private String instruments;
	private String statuses;
	private String settlementDate;
}
