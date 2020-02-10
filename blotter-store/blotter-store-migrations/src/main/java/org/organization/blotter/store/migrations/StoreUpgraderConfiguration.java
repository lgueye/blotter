package org.organization.blotter.store.migrations;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.sql.Driver;
import java.util.List;

@SuppressWarnings("unchecked")
@Configuration
public class StoreUpgraderConfiguration {

	@Bean
	public DataSource dataSource(@Value("${datasource.driverClassName}") final String driverClassName,
			@Value("${datasource.username}") final String userName, @Value("${datasource.url}") final String url,
			@Value("${datasource.password:}") final String password) throws ClassNotFoundException {
		final SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setDriverClass((Class<? extends Driver>) Class.forName(driverClassName));
		dataSource.setUsername(userName);
		dataSource.setUrl(url);
		dataSource.setPassword(password);
		return dataSource;
	}

	@Bean
	public JdbcTemplate jdbcTemplate(final DataSource datasource) {
		return new JdbcTemplate(datasource);
	}

	@Bean
	public Flyway flyway(@Value("${datasource.url}") final String url, final DataSource dataSource) {
		final Iterable<String> tokens = Splitter.on(":").split(url);
		final List<String> tokensAsList = Lists.newArrayList(tokens);
		if (CollectionUtils.isEmpty(tokensAsList)) {
			throw new IllegalArgumentException("Expected at least 3 tokens, got none");
		}
		final int tokenListSize = tokensAsList.size();
		if (tokenListSize < 3) {
			throw new IllegalArgumentException("Expected at least 3 token, got " + tokenListSize);
		}
		final String targetDb = tokensAsList.get(1);
		try {
			SupportedStore.valueOf(targetDb);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Supported databases are " + Lists.newArrayList(SupportedStore.values()) + ", got " + targetDb);
		}
		return Flyway.configure() //
				.dataSource(dataSource) //
				.baselineOnMigrate(true) //
				.locations("db/" + targetDb + "/migrations") //
				.placeholderPrefix("$_") //
				.load();
	}

}
