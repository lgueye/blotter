package org.organization.blotter.store.client;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author louis.gueye@gmail.com
 */
public interface NormalizedOrderRepository extends JpaRepository<NormalizedOrder, String> {

}
