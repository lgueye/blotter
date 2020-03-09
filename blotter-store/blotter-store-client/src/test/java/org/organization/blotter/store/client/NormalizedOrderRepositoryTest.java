package org.organization.blotter.store.client;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.organization.blotter.shared.model.MetaType;
import org.organization.blotter.shared.model.OrderStatus;
import org.organization.blotter.shared.model.TradeIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class intends to:
 * - showcase the repository usage
 * - test any complex DB interaction if relevant. For instance complex find by criteria should be correct at DB level before bubbling on higher levels. here is the proper class to extensively test various parameters combinations
 *
 * @author louis.gueye@gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BlotterStoreClientConfiguration.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class})
@Transactional
public class NormalizedOrderRepositoryTest {

	@Autowired
	private NormalizedOrderRepository underTest;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void create_ok() {
		// Given
		Instant now = Instant.now();
		final NormalizedOrder detached = NormalizedOrder.builder().price("5000").author("author").details("{}").externalIdentifier("ext-id-0000014")
				.instrument("LU788888").id(UUID.randomUUID().toString()).intent(TradeIntent.sell).metaType(MetaType.stex).portfolio("PF-2222")
				.status(OrderStatus.booked).timestamp(now).settlementDate(now.plus(Duration.ofDays(5))).build();

		// When
		underTest.save(detached);
	}

	@Test
	public void update_ok() {
		// Given
		final String id = UUID.randomUUID().toString();
		Instant now = Instant.now();
		final NormalizedOrder detached = NormalizedOrder.builder().price("5000").author("author").details("{}").externalIdentifier("ext-id-0000014")
				.instrument("LU788888").id(id).intent(TradeIntent.sell).metaType(MetaType.stex).portfolio("PF-2222").status(OrderStatus.booked)
				.timestamp(now).settlementDate(now.plus(Duration.ofDays(5))).build();
		underTest.save(detached);
		final Optional<NormalizedOrder> persistedOptional = underTest.findById(id);
		assertTrue(persistedOptional.isPresent());
		final NormalizedOrder persisted = persistedOptional.get();
		final String expected = "foo";
		persisted.setAuthor(expected);
		underTest.save(persisted);

		// When
		final Optional<NormalizedOrder> updatedOptional = underTest.findById(id);

		// Then
		assertTrue(updatedOptional.isPresent());
		final NormalizedOrder updated = updatedOptional.get();
		assertEquals(expected, updated.getAuthor());
	}

	@Test
	public void delete_ok() {
		// Given
		final String id = UUID.randomUUID().toString();
		Instant now = Instant.now();
		final NormalizedOrder detached = NormalizedOrder.builder().price("5000").author("author").details("{}").externalIdentifier("ext-id-0000014")
				.instrument("LU788888").id(id).intent(TradeIntent.sell).metaType(MetaType.stex).portfolio("PF-2222").status(OrderStatus.booked)
				.timestamp(now).settlementDate(now.plus(Duration.ofDays(5))).build();
		underTest.save(detached);
		underTest.deleteById(id);

		// When
		final Optional<NormalizedOrder> persistedOptional = underTest.findById(id);

		// Then
		assertFalse(persistedOptional.isPresent());
	}

	/**
	 * TODO implement me when exhaustive specs are available
	 */
	@Test
	public void find_by_criteria_ok() {

	}
}
