package org.organization.blotter.api.model;

import lombok.*;

import java.time.Instant;

/**
 * @author louis.gueye@gmail.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = {"portfolio", "instrument", "timestamp", "metaType", "intent"})
public class SearchOrderCriteria {
	private String externalIdentifiers;
	private String metaTypes;
	private String authors;
	private String portfolios;
	private Float amount;
	private String intents;
	private String instruments;
	private String statuses;
	private Instant timestamp;
}
