package org.organization.blotter.api.server.specific.stex;

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
@EqualsAndHashCode(of = {"portfolio", "instrument", "timestamp", "metaType", "intent"})
class StexOrderDto {
	private String id;
	private String externalIdentifier;
	private MetaType metaType = MetaType.stex;
	private String author;
	private String portfolio;
	private Float price;
	private TradeIntent intent;
	private String instrument;
	private Instant timestamp;
}
