package com.database;

import java.sql.Connection;
import java.sql.SQLException;

import com.api.utils.ConfigManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseManager {

	private static final String DB_URL = ConfigManager.getProperty("DB_URL");
	private static final String DB_USERNAME = ConfigManager.getProperty("DB_USER_NAME");
	private static final String DB_PASSWORD = ConfigManager.getProperty("DB_PASSWORD");
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

	// volatile - Any update that happens to it all thread will aware about it.
	private DatabaseManager() {

	}

	// synchronized - keyword used to make it thread safe means at time only one
	// thread can access it so there will not be any issue in parallel execution.
	private static void initializePool() {
		if (hikariDataSource == null) {
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
				}
			}
		}
	}

	public static Connection getConnection() throws SQLException {
		if (hikariDataSource == null) {
			initializePool();
		} else if (hikariDataSource.isClosed()) {
			throw new SQLException();
		}

		conn = hikariDataSource.getConnection();

		return conn;
	}
}
