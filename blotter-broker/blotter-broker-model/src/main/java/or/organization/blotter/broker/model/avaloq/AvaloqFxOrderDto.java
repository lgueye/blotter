package or.organization.blotter.broker.model.avaloq;

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
@EqualsAndHashCode(of = {"portfolio", "instrument", "timestamp", "metaType", "intent"})
public class AvaloqFxOrderDto {
	private String externalIdentifier;
	private MetaType metaType = MetaType.fx;
	private String author;
	private String portfolio;
	private Float price;
	private TradeIntent intent;
	private String instrument;
	private OrderStatus status;
	private Instant timestamp;
}
