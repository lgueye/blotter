package org.organization.blotter.store.client;

import org.jooq.DSLContext;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
import org.modelmapper.jooq.RecordValueReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author louis.gueye@gmail.com
 */
@Configuration
public class BlotterStoreClientConfiguration {

	@Bean
	public ModelMapper modelMapper() {
		final ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setSourceNameTokenizer(NameTokenizers.UNDERSCORE).addValueReader(new RecordValueReader());
		return mapper;
	}

	@Bean
	public BlotterStoreClient blotterStoreClient(final DSLContext dslContext, final ModelMapper modelMapper) {
		return new BlotterStoreClient(dslContext, modelMapper);
	}
}
