package org.organization.blotter.broker.consumer;

import or.organization.blotter.broker.model.SourceQueues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

/**
 * @author louis.gueye@gmail.com
 */
@RunWith(MockitoJUnitRunner.class)
public class BlotterBrokerConsumerTest {

	@Mock
	private BlotterBrokerReactor reactor;

	@InjectMocks
	private BlotterBrokerConsumer underTest;

	@Test
	public void on_avaloq_message_ok() {
		// Given
		final String message = "message";

		// When
		underTest.onAvaloqMessage(message);

		// Then
		verify(reactor).process(message, SourceQueues.AVALOQ);
	}

	@Test
	public void on_smart_trade_message_ok() {
		// Given
		final String message = "message";

		// When
		underTest.onSmartTradeMessage(message);

		// Then
		verify(reactor).process(message, SourceQueues.SMART_TRADE);
	}
}
