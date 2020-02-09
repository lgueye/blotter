package org.organization.blotter.api.server.specific.stex;

import org.organization.blotter.broker.consumer.ProcessingContext;

/**
 * @author louis.gueye@gmail.com
 */
public interface StextOrderDtoProducer {
	boolean accept(final ProcessingContext context);

	StexOrderDto produce(final ProcessingContext context);
}
