package org.organization.blotter.store.client.parsers.range.numeric;

import org.junit.Before;
import org.junit.Test;
import org.organization.blotter.store.client.parsers.range.numeric.FromFloatRangePredicateProducer;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FromFloatRangePredicateProducerTest {
	private FromFloatRangePredicateProducer underTest;

	@Before
	public void before() {
		underTest = new FromFloatRangePredicateProducer();
	}

	@Test
	public void accept_ok() {
		assertTrue(underTest.accept("[78.2["));
	}

	@Test
	public void accept_ko() {
		assertFalse(underTest.accept("[22.2,22.6]"));
		assertFalse(underTest.accept("[]"));
		assertFalse(underTest.accept("]25.4]"));
		assertFalse(underTest.accept("]]"));
		assertFalse(underTest.accept("[["));
	}
}