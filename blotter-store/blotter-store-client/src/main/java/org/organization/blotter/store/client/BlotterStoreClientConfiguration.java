package org.organization.blotter.store.client;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author louis.gueye@gmail.com
 */
@EnableAutoConfiguration
@Configuration
@EntityScan(basePackages = {"org.organization.blotter.store.client"})
@EnableJpaRepositories(basePackages = {"org.organization.blotter.store.client"})
@EnableTransactionManagement
public class BlotterStoreClientConfiguration {

	@Bean
	public PortfoliosCriteriaToPredicateProducer portfoliosCriteriaToPredicateProducer() {
		return new PortfoliosCriteriaToPredicateProducer();
	}

	@Bean
	public MetaTypesCriteriaToPredicateProducer metaTypesCriteriaToPredicateProducer() {
		return new MetaTypesCriteriaToPredicateProducer();
	}

	@Bean
	public BlotterStoreClient blotterStoreClient(final NormalizedOrderRepository repository, final EntityManager entityManager,
			final List<CriterionToPredicateProducer> predicatesProducers) {
		return new BlotterStoreClient(repository, entityManager, predicatesProducers);
	}
}
