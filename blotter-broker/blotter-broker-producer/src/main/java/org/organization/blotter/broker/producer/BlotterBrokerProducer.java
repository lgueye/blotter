package org.organization.blotter.broker.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;

/**
 * @author louis.gueye@gmail.com
 */
@RequiredArgsConstructor
public class BlotterBrokerProducer {

	private final JmsTemplate jmsTemplate;

	public void send(final String destination, final RawStexDto message) {
		jmsTemplate.convertAndSend(destination, message);
	}
}
