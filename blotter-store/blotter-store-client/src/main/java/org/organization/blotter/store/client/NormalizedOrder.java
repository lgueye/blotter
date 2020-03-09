package org.organization.blotter.store.client;

import lombok.*;
import org.organization.blotter.shared.model.MetaType;
import org.organization.blotter.shared.model.OrderStatus;
import org.organization.blotter.shared.model.TradeIntent;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
 * @author louis.gueye@gmail.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "normalized_orders", indexes = {@Index(name = "idx_normalized_orders_ext_id", columnList = "external_identifier"),
		@Index(name = "idx_normalized_orders_meta_type", columnList = "meta_type"),
		@Index(name = "idx_normalized_orders_status", columnList = "status"), @Index(name = "idx_normalized_orders_author", columnList = "author"),
		@Index(name = "idx_normalized_orders_intent", columnList = "intent"),
		@Index(name = "idx_normalized_orders_instrument", columnList = "instrument"),
		@Index(name = "idx_normalized_orders_portfolio", columnList = "portfolio"),
		@Index(name = "idx_normalized_orders_timestamp", columnList = "timestamp"),
		@Index(name = "idx_normalized_orders_setlmt_date", columnList = "settlement_date"),
		@Index(name = "idx_normalized_orders_uniq", columnList = "portfolio, instrument, meta_type, intent, settlement_date", unique = true)})
@EqualsAndHashCode(of = {"portfolio", "instrument", "metaType", "intent", "settlementDate"})
public class NormalizedOrder {
	@Id
	private String id;

	@Column(name = "timestamp", columnDefinition = "varchar(255)", nullable = false)
	@NotNull
	private Instant timestamp;
	@Column(name = "settlement_date", columnDefinition = "varchar(255)", nullable = false)
	@NotNull
	private Instant settlementDate;

	@Column(name = "external_identifier", nullable = false)
	@NotNull
	private String externalIdentifier;

	@Column(length = 50, name = "meta_type", nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	private MetaType metaType;

	@Column(nullable = false)
	@NotNull
	private String author;

	@Column(length = 50, nullable = false)
	@NotNull
	private String portfolio;

	@Column(nullable = false)
	@NotNull
	private String price;

	@Column(length = 50, nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	private TradeIntent intent;

	@Column(length = 50, nullable = false)
	@NotNull
	private String instrument;

	@Column(length = 50, nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	private OrderStatus status;

	@Column(length = 4000, nullable = false)
	@NotNull
	private String details;

}
