package org.organization.blotter.broker.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;

import java.time.Instant;
import java.util.List;

/**
 * @author louis.gueye@gmail.com
 */
@Slf4j
@RequiredArgsConstructor
public class AVQBlotterBrokerConsumer {

	private final List<IncomingMessageProcessor> processors;

	@JmsListener(destination = "AVQ", concurrency = "1", containerFactory = "jmsListenerContainerFactory")
	public void onMessage(@Payload final String message) {
		final ProcessingContext context = ProcessingContext.builder().source("AVQ").timestamp(Instant.now()).message(message).build();
		log.info("processing context {}", context);
		processors.forEach(processor -> processor.process(context));
	}

}
