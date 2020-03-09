package org.organization.blotter.api.model;

import lombok.*;
import org.organization.blotter.shared.model.MetaType;
import org.organization.blotter.shared.model.OrderStatus;
import org.organization.blotter.shared.model.TradeIntent;

import java.time.Instant;

/**
 * @author louis.gueye@gmail.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = {"portfolio", "instrument", "metaType", "intent", "status"})
public class NormalizedOrderDto {
	private String id;
	private String externalIdentifier;
	private MetaType metaType;
	private String author;
	private String portfolio;
	private Float price;
	private TradeIntent intent;
	private String instrument;
	private String details;
	private OrderStatus status;
	private Instant timestamp;
}
