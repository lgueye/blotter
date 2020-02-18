package org.organization.blotter.broker.producer;

import lombok.RequiredArgsConstructor;
import or.organization.blotter.broker.model.avaloq.AvaloqFxOrderDto;
import or.organization.blotter.broker.model.avaloq.AvaloqStexOrderDto;
import or.organization.blotter.broker.model.SourceQueues;
import or.organization.blotter.broker.model.smarttrade.SmartTradeFxOrderDto;
import org.springframework.jms.core.JmsTemplate;

/**
 * @author louis.gueye@gmail.com
 */
@RequiredArgsConstructor
public class BlotterBrokerProducer {

	private final JmsTemplate jmsTemplate;

	public void send(final AvaloqStexOrderDto message) {
		jmsTemplate.convertAndSend(SourceQueues.AVALOQ, message);
	}

	public void send(final AvaloqFxOrderDto message) {
		jmsTemplate.convertAndSend(SourceQueues.AVALOQ, message);
	}

	public void send(final SmartTradeFxOrderDto message) {
		jmsTemplate.convertAndSend(SourceQueues.SMART_TRADE, message);
	}
}
