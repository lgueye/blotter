package org.organization.blotter.api.server.normalized;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import or.organization.blotter.broker.model.SourceQueues;
import or.organization.blotter.broker.model.smarttrade.SmartTradeFxOrderDto;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.organizarion.blotter.notification.model.OrderNotificationDto;
import org.organization.blotter.api.model.NormalizedOrderDto;
import org.organization.blotter.broker.consumer.ProcessingContext;
import org.organization.blotter.notification.producer.BlotterNotificationProducer;
import org.organization.blotter.shared.configuration.BlotterSharedSerializationConfiguration;
import org.organization.blotter.shared.model.MetaType;
import org.organization.blotter.shared.model.OrderStatus;
import org.organization.blotter.shared.model.TradeIntent;
import org.organization.blotter.store.client.BlotterStoreClient;

import java.time.Instant;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author louis.gueye@gmail.com
 */
@RunWith(MockitoJUnitRunner.class)
public class NormalizationProcessorTest {

	private ObjectMapper objectMapper;
	@Mock
	private BlotterStoreClient persistenceService;

	@Mock
	private BlotterNotificationProducer orderNotificationService;

	private NormalizationProcessor underTest;

	@Before
	public void before() {
		objectMapper = new BlotterSharedSerializationConfiguration().objectMapper();
		final List<NormalizedOrderDtoProducer> normalizedOrderProducers = Lists.newArrayList(new AvaloqFxOrderDtoToNormalizedOrderDtoProducer(
				objectMapper), new AvaloqStexOrderDtoToNormalizedOrderDtoProducer(objectMapper),
				new SmartTradeFxOrderDtoToNormalizedOrderDtoProducer(objectMapper));
		final OrderNotificationDtoProducer orderNotificationProducer = new OrderNotificationDtoProducer();
		underTest = new NormalizationProcessor(normalizedOrderProducers, persistenceService, orderNotificationProducer, orderNotificationService);
	}

	@Test
	public void process_ok() throws JsonProcessingException {
		// Given
		final Instant now = Instant.now();
		final OrderStatus status = OrderStatus.placed;
		final MetaType metaType = MetaType.fx;
		final String portfolio = "pf-0002";
		final TradeIntent intent = TradeIntent.sell;
		final String instrument = "LUAXXXXXXX";
		final String externalIdentifier = "ext-id";
		final String author = "louis";
		final float amount = 450000.00f;
		final SmartTradeFxOrderDto dto = SmartTradeFxOrderDto.builder() //
				.metaType(metaType) //
				.timestamp(now) //
				.status(status) //
				.portfolio(portfolio) //
				.intent(intent) //
				.instrument(instrument) //
				.externalIdentifier(externalIdentifier) //
				.author(author) //
				.amount(amount) //
				.build();
		final ProcessingContext context = ProcessingContext.builder().message(objectMapper.writeValueAsString(dto)).source(SourceQueues.SMART_TRADE)
				.timestamp(now).build();
		final String id = "id";
		final ArgumentCaptor<NormalizedOrderDto> normalizedOrderArgumentCaptor = ArgumentCaptor.forClass(NormalizedOrderDto.class);
		when(persistenceService.save(normalizedOrderArgumentCaptor.capture())).thenReturn(id);

		// When
		underTest.process(context);

		// Then
		verify(persistenceService).save(normalizedOrderArgumentCaptor.capture());
		final NormalizedOrderDto capturedNormalizedOrder = normalizedOrderArgumentCaptor.getValue();
		assertNotNull(capturedNormalizedOrder);
		assertEquals(metaType, capturedNormalizedOrder.getMetaType());
		assertEquals(now, capturedNormalizedOrder.getTimestamp());
		assertEquals(status, capturedNormalizedOrder.getStatus());
		assertEquals(portfolio, capturedNormalizedOrder.getPortfolio());
		assertEquals(intent, capturedNormalizedOrder.getIntent());
		assertEquals(instrument, capturedNormalizedOrder.getInstrument());
		assertEquals(externalIdentifier, capturedNormalizedOrder.getExternalIdentifier());
		assertEquals(author, capturedNormalizedOrder.getAuthor());
		assertEquals(amount, capturedNormalizedOrder.getAmount());
		assertNull(capturedNormalizedOrder.getId());
		assertNotNull(capturedNormalizedOrder.getDetails());

		final ArgumentCaptor<OrderNotificationDto> orderNotificationArgumentCaptor = ArgumentCaptor.forClass(OrderNotificationDto.class);
		verify(orderNotificationService).send(orderNotificationArgumentCaptor.capture());
		final OrderNotificationDto capturedOrderNotification = orderNotificationArgumentCaptor.getValue();
		assertNotNull(capturedOrderNotification);
		assertEquals(metaType, capturedOrderNotification.getMetaType());
		assertEquals(now, capturedOrderNotification.getTimestamp());
		assertEquals(status, capturedOrderNotification.getStatus());
		assertEquals(portfolio, capturedOrderNotification.getPortfolio());
		assertEquals(intent, capturedOrderNotification.getIntent());
		assertEquals(instrument, capturedOrderNotification.getInstrument());
		assertEquals(externalIdentifier, capturedOrderNotification.getExternalIdentifier());
		assertEquals(author, capturedOrderNotification.getAuthor());
		assertEquals(amount, capturedOrderNotification.getAmount());
		assertEquals(id, capturedOrderNotification.getId());
	}

}
