package org.organization.blotter.store.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.sql.SQLException;

/**
 * @author louis.gueye@gmail.com
 */
@RequiredArgsConstructor
@Slf4j
public class H2Server {
	private final Integer port;
	private final String baseDir;
	private Server server;

	@PostConstruct
	public void postConstruct() throws SQLException {
		server = Server.createTcpServer("-tcpPort", String.valueOf(port), "-tcpAllowOthers", "-ifNotExists", "-tcpDaemon", "-baseDir", baseDir);
		server.start();
		log.info("Started blotter store server (H2 tcp server)");
		log.info("Listening on {}", port);
		log.info("Writing to {}", baseDir);
	}

	@PreDestroy
	public void preDestroy() {
		server.stop();
	}
}
