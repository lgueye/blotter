package org.organization.blotter.api.server.normalized;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import or.organization.blotter.broker.model.SourceQueues;
import or.organization.blotter.broker.model.avaloq.AvaloqFxOrderDto;
import org.junit.Before;
import org.junit.Test;
import org.organization.blotter.api.model.NormalizedOrderDto;
import org.organization.blotter.broker.consumer.ProcessingContext;
import org.organization.blotter.shared.configuration.BlotterSharedSerializationConfiguration;
import org.organization.blotter.shared.model.MetaType;
import org.organization.blotter.shared.model.TradeIntent;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author louis.gueye@gmail.com
 */
public class AvaloqFxOrderDtoToNormalizedOrderDtoProducerTest {

	private AvaloqFxOrderDtoToNormalizedOrderDtoProducer underTest;
	private ObjectMapper objectMapper;

	@Before
	public void before() {
		objectMapper = new BlotterSharedSerializationConfiguration().objectMapper();
		underTest = new AvaloqFxOrderDtoToNormalizedOrderDtoProducer(objectMapper);
	}

	@Test
	public void accept_ok() throws JsonProcessingException {
		// Given
		final AvaloqFxOrderDto dto = AvaloqFxOrderDto.builder().author("author").externalIdentifier("ext-int").intent(TradeIntent.buy)
				.portfolio("pf-001").build();

		// When
		final boolean actual = underTest.accept(ProcessingContext.builder().message(objectMapper.writeValueAsString(dto)).source(SourceQueues.AVALOQ)
				.timestamp(Instant.now()).build());

		// Then
		assertTrue(actual);
	}

	@Test
	public void accept_ko_wrong_metatype() throws JsonProcessingException {
		// Given
		final AvaloqFxOrderDto dto = AvaloqFxOrderDto.builder().author("author").externalIdentifier("ext-int").intent(TradeIntent.buy)
				.metaType(MetaType.stex).portfolio("pf-001").build();
		final ProcessingContext context = ProcessingContext.builder().message(objectMapper.writeValueAsString(dto)).source(SourceQueues.AVALOQ)
				.timestamp(Instant.now()).build();

		// When
		final boolean actual = underTest.accept(context);

		// Then
		assertFalse(actual);
	}

	@Test
	public void accept_ko_wrong_source() throws JsonProcessingException {
		// Given
		final AvaloqFxOrderDto dto = AvaloqFxOrderDto.builder().author("author").externalIdentifier("ext-int").intent(TradeIntent.buy)
				.portfolio("pf-001").build();
		final ProcessingContext context = ProcessingContext.builder().message(objectMapper.writeValueAsString(dto)).source(SourceQueues.SMART_TRADE)
				.timestamp(Instant.now()).build();

		// When
		final boolean actual = underTest.accept(context);

		// Then
		assertFalse(actual);
	}

	@Test
	public void produce_ok() throws JsonProcessingException {
		// Given
		final AvaloqFxOrderDto dto = AvaloqFxOrderDto.builder().author("author").externalIdentifier("ext-int").intent(TradeIntent.buy)
				.portfolio("pf-001").build();
		final ProcessingContext context = ProcessingContext.builder().message(objectMapper.writeValueAsString(dto)).source(SourceQueues.AVALOQ)
				.timestamp(Instant.now()).build();

		// When
		final NormalizedOrderDto actual = underTest.produce(context);

		// Then
		final NormalizedOrderDto expected = NormalizedOrderDto.builder().author("author").externalIdentifier("ext-int").metaType(MetaType.fx)
				.intent(TradeIntent.buy).portfolio("pf-001").build();
		assertEquals(expected, actual);
	}
}
