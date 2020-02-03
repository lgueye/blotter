package org.organization.blotter.broker.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;

/**
 * @author louis.gueye@gmail.com
 */
@RequiredArgsConstructor
public class BlotterBrokerProducer {

	private final JmsTemplate jmsTemplate;

	public void send(final RawStexDto message) {
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
