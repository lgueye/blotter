package org.organization.blotter.broker.server;

import org.apache.activemq.broker.BrokerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author louis.gueye@gmail.com
 */
@Configuration
public class BlotterBrokerServerConfiguration {

	@Bean
	public BrokerService brokerService(@Value("${spring.activemq.broker-url}") final String brokerUrl) throws Exception {
		BrokerService broker = new BrokerService();
		broker.addConnector(brokerUrl);
		broker.setPersistent(false);
		broker.setUseJmx(false);
		broker.start();
		return broker;
	}

}
