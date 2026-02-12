package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.api.utils.ConfigManager;

public class DatabaseManagerOld {

	private static final String DB_URL = ConfigManager.getProperty("DB_URL");
	private static final String DB_USERNAME = ConfigManager.getProperty("DB_USER_NAME");
	private static final String DB_PASSWORD = ConfigManager.getProperty("DB_PASSWORD");
	private volatile static Connection conn;
    // volatile - Any update that happens to it all thread will aware about it.
	private DatabaseManagerOld() {

	}

	// synchronized - keyword used to make it thread safe means at time only one
	// thread can access it so there will not be any issue in parallel execution.
	public static void createConnection() throws SQLException {
		if (conn == null) {
			synchronized (DatabaseManagerOld.class) {// 

				if (conn == null) { // Only and only for the first connection request
					conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
					System.out.println(conn);
				}
			}
		}
	}
}
