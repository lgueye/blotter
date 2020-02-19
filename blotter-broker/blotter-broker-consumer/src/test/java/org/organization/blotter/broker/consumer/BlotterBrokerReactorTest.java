package org.organization.blotter.broker.consumer;

import or.organization.blotter.broker.model.SourceQueues;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author louis.gueye@gmail.com
 */
public class BlotterBrokerReactorTest {
	private IncomingMessageProcessor p1;
	private IncomingMessageProcessor p2;
	private BlotterBrokerReactor underTest;

	@Before
	public void before() {
		p1 = mock(IncomingMessageProcessor.class);
		p2 = mock(IncomingMessageProcessor.class);
		underTest = new BlotterBrokerReactor(Lists.newArrayList(p1, p2));
	}

	@Test
	public void process_ok() {
		// Given
		final String message = "message";
		final String source = SourceQueues.SMART_TRADE;
		final ArgumentCaptor<ProcessingContext> argumentCaptor = ArgumentCaptor.forClass(ProcessingContext.class);

		// When
		underTest.process(message, source);

		// Then
		verify(p1).process(argumentCaptor.capture());
		verify(p2).process(argumentCaptor.capture());
		final List<ProcessingContext> captured = argumentCaptor.getAllValues();
		final ProcessingContext processingContext = captured.get(0);
		assertSame(processingContext, captured.get(1));
		assertEquals(message, processingContext.getMessage());
		assertEquals(source, processingContext.getSource());
		assertNotNull(processingContext.getTimestamp());
	}
}
