package org.organizarion.blotter.notification.model;

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
@Builder(toBuilder = true)
@EqualsAndHashCode(of = {"portfolio", "instrument", "metaType", "intent"})
public class OrderNotificationDto {
	private String id;
	private String externalIdentifier;
	private MetaType metaType;
	private String author;
	private String portfolio;
	private Float price;
	private TradeIntent intent;
	private String instrument;
	private OrderStatus status;
	private Instant timestamp;
}
