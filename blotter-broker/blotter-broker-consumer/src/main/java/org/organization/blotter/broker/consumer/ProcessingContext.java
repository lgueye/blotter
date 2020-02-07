package org.organization.blotter.broker.consumer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * @author louis.gueye@gmail.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessingContext {
	private Instant timestamp;
	private String source;
	private String message;
}
