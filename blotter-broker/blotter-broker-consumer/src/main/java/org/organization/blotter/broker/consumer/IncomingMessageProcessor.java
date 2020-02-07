package org.organization.blotter.broker.consumer;

/**
 * @author louis.gueye@gmail.com
 */
public interface IncomingMessageProcessor {
	void process(final ProcessingContext context);
}
