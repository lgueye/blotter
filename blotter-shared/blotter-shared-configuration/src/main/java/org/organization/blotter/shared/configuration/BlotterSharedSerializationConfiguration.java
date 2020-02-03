package org.organization.blotter.shared.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * @author louis.gueye@gmail.com
 */
@Configuration
public class BlotterSharedSerializationConfiguration {

	/**
	 * ObjectMapper configuration
	 * - we are very likely to not be interested in null values, they usually bring noise
	 * - we write Instants as iso 8601 timestamps
	 * - we do not want a de-serialization to fail if an field can't be mapped to the DTO.
	 * It's very likely that the field is the result of the serialization of a getXXXX method which has no corresponding field.
	 * This very valid and cohesive object oriented pattern is used when dynamically computing values from other fields.
	 *
	 * @return
	 */
	@Primary
	@Bean
	public ObjectMapper objectMapper() {
		return Jackson2ObjectMapperBuilder.json() //
				.serializationInclusion(JsonInclude.Include.NON_NULL) // Don’t include null values
				.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) // use ISODate and not other cryptic formats
				.featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS) // allow empty json to be produced
				.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES) // do not fail on unkown properties because they are likely to
				.modules(new JavaTimeModule(), new GuavaModule()) //
				.build();
	}

}
