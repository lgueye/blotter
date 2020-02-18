package org.organization.blotter.store.server;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;

/**
 * @author louis.gueye@gmail.com
 */
@RequiredArgsConstructor
@Slf4j
public class MySQLServer {
	private final Integer port;
	private final String schema;
	private final String user;
	private final String password;
	private EmbeddedMysql server;

	@PostConstruct
	public void postConstruct() {
		final MysqldConfig config = aMysqldConfig(v5_7_latest).withCharset(UTF8).withPort(port).withUser(user, password)
				.withTimeZone("Europe/Zurich").withTimeout(2, TimeUnit.MINUTES).build();
		server = anEmbeddedMysql(config).addSchema(schema).start();
	}

	@PreDestroy
	public void preDestroy() {
		server.stop();
	}
}
