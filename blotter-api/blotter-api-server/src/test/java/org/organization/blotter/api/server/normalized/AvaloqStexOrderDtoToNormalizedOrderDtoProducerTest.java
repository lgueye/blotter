package org.organization.blotter.api.server.normalized;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import or.organization.blotter.broker.model.SourceQueues;
import or.organization.blotter.broker.model.avaloq.AvaloqStexOrderDto;
import org.junit.Before;
import org.junit.Test;
import org.organization.blotter.api.model.NormalizedOrderDto;
import org.organization.blotter.broker.consumer.ProcessingContext;
import org.organization.blotter.shared.configuration.BlotterSharedSerializationConfiguration;
import org.organization.blotter.shared.model.MetaType;
import org.organization.blotter.shared.model.TradeIntent;

import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author louis.gueye@gmail.com
 */
public class AvaloqStexOrderDtoToNormalizedOrderDtoProducerTest {

	private AvaloqStexOrderDtoToNormalizedOrderDtoProducer underTest;
	private ObjectMapper objectMapper;

	@Before
	public void before() {
		objectMapper = new BlotterSharedSerializationConfiguration().objectMapper();
		underTest = new AvaloqStexOrderDtoToNormalizedOrderDtoProducer(objectMapper);
	}

	@Test
	public void accept_ok() throws JsonProcessingException {
		// Given
		final AvaloqStexOrderDto dto = AvaloqStexOrderDto.builder().author("author").externalIdentifier("ext-int").intent(TradeIntent.buy)
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
		final AvaloqStexOrderDto dto = AvaloqStexOrderDto.builder().author("author").externalIdentifier("ext-int").intent(TradeIntent.buy)
				.metaType(MetaType.fx).portfolio("pf-001").build();
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
		final AvaloqStexOrderDto dto = AvaloqStexOrderDto.builder().author("author").externalIdentifier("ext-int").intent(TradeIntent.buy)
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
		final Instant now = Instant.now();
		final Instant settlementDate = now.plus(Duration.ofDays(1));
		final AvaloqStexOrderDto dto = AvaloqStexOrderDto.builder().author("author").externalIdentifier("ext-int").intent(TradeIntent.buy)
				.portfolio("pf-001").price(600f).settlementDate(settlementDate).build();
		final ProcessingContext context = ProcessingContext.builder().message(objectMapper.writeValueAsString(dto)).source(SourceQueues.AVALOQ)
				.timestamp(now).build();

		// When
		final NormalizedOrderDto actual = underTest.produce(context);

		// Then
		final NormalizedOrderDto expected = NormalizedOrderDto.builder().author("author").externalIdentifier("ext-int").metaType(MetaType.stex)
				.intent(TradeIntent.buy).portfolio("pf-001").price(600f).settlementDate(settlementDate).build();
		assertEquals(expected, actual);
	}
}
