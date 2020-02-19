package org.organization.blotter.broker.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.List;

/**
 * @author louis.gueye@gmail.com
 */
@RequiredArgsConstructor
@Slf4j
public class BlotterBrokerReactor {
	private final List<IncomingMessageProcessor> processors;

	void process(String message, String source) {
		final ProcessingContext context = ProcessingContext.builder().source(source).timestamp(Instant.now()).message(message).build();
		log.info("processing context {}", context);
		processors.forEach(processor -> processor.process(context));
	}
}
