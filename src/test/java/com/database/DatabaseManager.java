package com.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.utils.ConfigManager;
import com.api.utils.EnvUtil;
import com.api.utils.VaultDBConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseManager {
	private static final Logger LOGGER = LogManager.getLogger(DatabaseManager.class);
	
	private static final int MAXIMUM_POOL_SIZE = Integer.parseInt(ConfigManager.getProperty("MAXIMUM_POOL_SIZE"));
	private static final int MINIMUM_IDLE_COUNT = Integer.parseInt(ConfigManager.getProperty("MINIMUM_IDLE_COUNT"));
	private static final int CONNECTION_TIMEOUT_IN_SECS = Integer
			.parseInt(ConfigManager.getProperty("CONNECTION_TIMEOUT_IN_SECS"));
	private static final int IDLE_TIMEOUT_SECS = Integer.parseInt(ConfigManager.getProperty("IDLE_TIMEOUT_SECS"));
	private static final int MAX_LIFE_TIME_IN_MIN = Integer.parseInt(ConfigManager.getProperty("MAX_LIFE_TIME_IN_MIN"));
	private static final String HIKARI_POOL_NAME = ConfigManager.getProperty("HIKARI_POOL_NAME");

	private static HikariConfig hikariConfig;
	private volatile static HikariDataSource hikariDataSource;
	private volatile static Connection conn;

	private static boolean isVaultUp = true;
	private static final String DB_URL = loadSecret("DB_URL");
	private static final String DB_USERNAME = loadSecret("DB_USERNAME");
	private static final String DB_PASSWORD = loadSecret("DB_PASSWORD");

	// volatile - Any update that happens to it all thread will aware about it.
	private DatabaseManager() {

	}
	
	public static String loadSecret(String key) {

		String value = null;
		if (isVaultUp) {
			// value will get its value from either Vault or Env
			value = VaultDBConfig.getSecret(key);

			if (value == null) {
				LOGGER.error("Vault is down!! or some issue with vault");
				isVaultUp = false;
			} else {
				LOGGER.info("Reading Value From Vault...",key);
				return value;
			}
		}
		// We need to pick up data from Env!!
		LOGGER.info("Reading Value From ENV...");
		value = EnvUtil.getValue(key);
		return value;
	}

	// synchronized - keyword used to make it thread safe means at time only one
	// thread can access it so there will not be any issue in parallel execution.
	private static void initializePool() {
		if (hikariDataSource == null) {
			
			LOGGER.warn("Datatbase connection is not available...Creating HikariDataSource");
			
			synchronized (DatabaseManager.class) {//

				if (hikariDataSource == null) { // Only and only for the first connection request
					hikariConfig = new HikariConfig();

					hikariConfig.setJdbcUrl(DB_URL);
					hikariConfig.setUsername(DB_USERNAME);
					hikariConfig.setPassword(DB_PASSWORD);
					hikariConfig.setMaximumPoolSize(MAXIMUM_POOL_SIZE);
					hikariConfig.setMinimumIdle(MINIMUM_IDLE_COUNT);
					hikariConfig.setConnectionTimeout(CONNECTION_TIMEOUT_IN_SECS * 1000);// 10 sec
					hikariConfig.setIdleTimeout(IDLE_TIMEOUT_SECS * 1000);
					hikariConfig.setMaxLifetime(MAX_LIFE_TIME_IN_MIN * 60 * 1000);// 30min 30*60*1000
					hikariConfig.setPoolName(HIKARI_POOL_NAME);
					hikariDataSource = new HikariDataSource(hikariConfig);
					LOGGER.info("Hikari Database created!!!");

				}
			}
		}
	}

	public static Connection getConnection() throws SQLException {
		if (hikariDataSource == null) {
			LOGGER.info("Initializing the Database connection using HikariCP");

			
			initializePool();
		} else if (hikariDataSource.isClosed()) {
			LOGGER.error("Hikari DATA Source is closed");

			throw new SQLException();
		}

		conn = hikariDataSource.getConnection();

		return conn;
	}
}
