package org.organization.blotter.broker.producer;

import or.organization.blotter.broker.model.SourceQueues;
import or.organization.blotter.broker.model.avaloq.AvaloqFxOrderDto;
import or.organization.blotter.broker.model.avaloq.AvaloqStexOrderDto;
import or.organization.blotter.broker.model.smarttrade.SmartTradeFxOrderDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jms.core.JmsTemplate;

import static org.mockito.Mockito.verify;

/**
 * @author louis.gueye@gmail.com
 */
@RunWith(MockitoJUnitRunner.class)
public class BlotterBrokerProducerTest {
	@Mock
	private JmsTemplate jmsTemplate;
	@InjectMocks
	private BlotterBrokerProducer underTest;

	@Test
	public void send_avaloq_stex_ok() {
		// Given
		final AvaloqStexOrderDto message = AvaloqStexOrderDto.builder().build();

		// When
		underTest.send(message);

		// Then
		verify(jmsTemplate).convertAndSend(SourceQueues.AVALOQ, message);
	}

	@Test
	public void send_avaloq_fx_ok() {
		// Given
		final AvaloqFxOrderDto message = AvaloqFxOrderDto.builder().build();

		// When
		underTest.send(message);

		// Then
		verify(jmsTemplate).convertAndSend(SourceQueues.AVALOQ, message);
	}

	@Test
	public void send_smart_trade_fx_ok() {
		// Given
		final SmartTradeFxOrderDto message = SmartTradeFxOrderDto.builder().build();

		// When
		underTest.send(message);

		// Then
		verify(jmsTemplate).convertAndSend(SourceQueues.SMART_TRADE, message);
	}
}
