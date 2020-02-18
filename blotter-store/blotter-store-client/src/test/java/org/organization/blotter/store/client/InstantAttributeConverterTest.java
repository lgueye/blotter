package org.organization.blotter.store.client;

import org.junit.Before;
import org.junit.Test;

import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class InstantAttributeConverterTest {
	private InstantAttributeConverter underTest;

	@Before
	public void setup() {
		underTest = new InstantAttributeConverter();
	}

	@Test
	public void nullGivesNull() {
		assertNull(underTest.convertToDatabaseColumn(null));
		assertNull(underTest.convertToEntityAttribute(null));
	}

	@Test
	public void conversionShouldSucceed() {
		final String stored = "2020-02-18T22:05:46.053Z";
		final Instant instant = Instant.parse(stored);
		assertEquals(stored, underTest.convertToDatabaseColumn(instant));
		assertEquals(instant, underTest.convertToEntityAttribute(stored));
	}
}
