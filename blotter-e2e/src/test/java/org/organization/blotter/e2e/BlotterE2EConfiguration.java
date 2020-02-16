package org.organization.blotter.e2e;

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
			@Value("${blotter.store.server.user}") final String username, @Value("${blotter.store.server.password}") final String password) {
		final SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}
}
