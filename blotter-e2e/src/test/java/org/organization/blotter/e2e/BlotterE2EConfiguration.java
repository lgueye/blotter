package org.organization.blotter.e2e;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.organization.blotter.api.consumer.BlotterApiConsumerConfiguration;
import org.organization.blotter.broker.producer.BlotterBrokerProducerConfiguration;
import org.organization.blotter.notification.consumer.BlotterNotificationConsumerConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

/**
 * @author louis.gueye@gmail.com
 */
@Import({BlotterApiConsumerConfiguration.class, BlotterNotificationConsumerConfiguration.class, BlotterBrokerProducerConfiguration.class})
@Configuration
public class BlotterE2EConfiguration {

	@Bean
	public DataSource dataSource(@Value("${blotter.store.server.url}") final String url,
			@Value("${blotter.store.server.user}") final String username, @Value("${blotter.store.server.password}") final String password,
			@Value("${blotter.store.server.schema}") final String schema, @Value("${blotter.store.client.driver}") final String driverClassName) {
		final HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setJdbcUrl(url);
		hikariConfig.setUsername(username);
		hikariConfig.setPassword(password);
		hikariConfig.setPoolName("e2e-hikari");
		hikariConfig.setConnectionTestQuery("select 1");
		hikariConfig.setDriverClassName(driverClassName);
		hikariConfig.setMinimumIdle(2);
		hikariConfig.setMaximumPoolSize(10);
		hikariConfig.setSchema(schema);
		return new HikariDataSource(hikariConfig);
	}
}
