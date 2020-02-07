package org.organization.blotter.api.server.normalized;

import org.organization.blotter.api.model.NormalizedOrderDto;
import org.organization.blotter.broker.consumer.ProcessingContext;

/**
 * @author louis.gueye@gmail.com
 */
public interface NormalizedOrderDtoProducer {
	boolean accept(final ProcessingContext context);

	NormalizedOrderDto produce(final ProcessingContext context);
}
