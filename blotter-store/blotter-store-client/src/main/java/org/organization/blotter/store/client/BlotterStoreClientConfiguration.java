package org.organization.blotter.store.client;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.RenderNameCase;
import org.jooq.conf.Settings;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author louis.gueye@gmail.com
 */
@Configuration
public class BlotterStoreClientConfiguration {

	@Bean
	public DSLContext dslContext(final DataSource dataSource) {
		return new DefaultDSLContext(dataSource, SQLDialect.MYSQL, new Settings().withRenderNameCase(RenderNameCase.LOWER));
	}

	@Bean
	public BlotterStoreClient blotterStoreClient(final DSLContext dslContext) {
		return new BlotterStoreClient(dslContext);
	}
}
