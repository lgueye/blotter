package org.organization.blotter.broker.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.organization.blotter.shared.configuration.BlotterSharedSerializationConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.ConnectionFactory;

/**
 * @author louis.gueye@gmail.com
 */
@Import({BlotterSharedSerializationConfiguration.class})
@Configuration
public class BlotterBrokerProducerConfiguration {

	static final String BROKER_URL_KEY = "blotter.broker.server.url";

	@Bean
	public ConnectionFactory connectionFactory(@Value("${" + BROKER_URL_KEY + "}") String brokerUrl) {
		return new ActiveMQConnectionFactory(brokerUrl);
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
	public JmsTemplate jmsTemplate(final ConnectionFactory connectionFactory, final MessageConverter messageConverter) {
		final JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
		jmsTemplate.setMessageConverter(messageConverter);
		return jmsTemplate;

	}

	@Bean
	public BlotterBrokerProducer blotterBrokerProducer(final JmsTemplate jmsTemplate) {
		return new BlotterBrokerProducer(jmsTemplate);
	}
}
