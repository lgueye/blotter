package org.organization.blotter.api.model;

import lombok.*;
import org.organization.blotter.shared.model.MetaType;
import org.organization.blotter.shared.model.TradeIntent;

import java.time.Instant;

/**
 * @author louis.gueye@gmail.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = {"portfolio", "instrument", "metaType", "intent"})
public class NormalizedOrderDto {
	private String id;
	private String externalIdentifier;
	private MetaType metaType;
	private String author;
	private String portfolio;
	private Float amount;
	private TradeIntent intent;
	private String instrument;
	private Instant timestamp;
}
