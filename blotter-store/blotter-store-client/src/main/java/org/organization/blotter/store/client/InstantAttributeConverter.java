package org.organization.blotter.store.client;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Instant;

@Converter(autoApply = true)
public class InstantAttributeConverter implements AttributeConverter<Instant, String> {
	@Override
	public String convertToDatabaseColumn(Instant entityValue) {
		if (entityValue == null) {
			return null;
		}

		return entityValue.toString();
	}

	@Override
	public Instant convertToEntityAttribute(String databaseValue) {
		if (databaseValue == null) {
			return null;
		}

		return Instant.parse(databaseValue);
	}
}
