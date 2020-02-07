package org.organization.blotter.broker.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.organization.blotter.shared.configuration.BlotterSharedSerializationConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import java.util.List;

/**
 * @author louis.gueye@gmail.com
 */
@EnableJms
@Import({BlotterSharedSerializationConfiguration.class})
@Configuration
public class BlotterBrokerConsumerConfiguration {

	@Bean
	public ActiveMQConnectionFactory activeMQConnectionFactory(@Value("${blotter.broker.server.url}") final String brokerUrl) {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL(brokerUrl);
		return connectionFactory;
	}

	@Bean
	public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(final ActiveMQConnectionFactory activeMQConnectionFactory) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(activeMQConnectionFactory);
		factory.setConcurrency("1-1");
		return factory;
	}

	@Bean
	public MessageConverter converter(final ObjectMapper objectMapper) {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setObjectMapper(objectMapper);
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}

	@Bean
	public AVQBlotterBrokerConsumer blotterBrokerConsumer(final List<IncomingMessageProcessor> processors) {
		return new AVQBlotterBrokerConsumer(processors);
	}
}
