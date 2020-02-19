package org.organization.blotter.broker.consumer;

import lombok.RequiredArgsConstructor;
import or.organization.blotter.broker.model.SourceQueues;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;

/**
 * @author louis.gueye@gmail.com
 */
@RequiredArgsConstructor
public class BlotterBrokerConsumer {

	private final BlotterBrokerReactor reactor;

	@JmsListener(destination = SourceQueues.AVALOQ, concurrency = "1", containerFactory = "jmsListenerContainerFactory")
	public void onAvaloqMessage(@Payload final String message) {
		reactor.process(message, SourceQueues.AVALOQ);
	}

	@JmsListener(destination = SourceQueues.SMART_TRADE, concurrency = "1", containerFactory = "jmsListenerContainerFactory")
	public void onSmartTradeMessage(@Payload final String message) {
		reactor.process(message, SourceQueues.SMART_TRADE);
	}

}
