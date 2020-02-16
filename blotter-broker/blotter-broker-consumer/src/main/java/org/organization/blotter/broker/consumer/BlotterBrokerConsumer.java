package org.organization.blotter.broker.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import or.organization.blotter.broker.model.SourceQueues;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;

import java.time.Instant;
import java.util.List;

/**
 * @author louis.gueye@gmail.com
 */
@Slf4j
@RequiredArgsConstructor
public class BlotterBrokerConsumer {

	private final List<IncomingMessageProcessor> processors;

	void processMessage(@Payload String message) {
		final ProcessingContext context = ProcessingContext.builder().source(SourceQueues.AVALOQ).timestamp(Instant.now()).message(message).build();
		log.info("processing context {}", context);
		processors.forEach(processor -> processor.process(context));
	}

	@JmsListener(destination = SourceQueues.AVALOQ, concurrency = "1", containerFactory = "jmsListenerContainerFactory")
	public void onAvaloqMessage(@Payload final String message) {
		processMessage(message);
	}

	@JmsListener(destination = SourceQueues.SMART_TRADE, concurrency = "1", containerFactory = "jmsListenerContainerFactory")
	public void onSmartTradeMessage(@Payload final String message) {
		processMessage(message);
	}

}
