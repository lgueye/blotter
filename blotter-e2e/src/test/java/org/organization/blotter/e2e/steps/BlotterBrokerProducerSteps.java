package org.organization.blotter.e2e.steps;

import io.cucumber.java8.En;
import org.organization.blotter.broker.producer.BlotterBrokerProducer;
import org.organization.blotter.broker.producer.RawStexDto;

import java.util.List;

/**
 * @author louis.gueye@gmail.com
 */
public class BlotterBrokerProducerSteps implements En {

	public BlotterBrokerProducerSteps(final BlotterBrokerProducer blotterBrokerProducer) {
		Given("blotter system receives the messages below:", (List<RawStexDto> messages) -> messages.forEach(blotterBrokerProducer::send));
	}

}
