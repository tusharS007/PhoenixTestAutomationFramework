package com.database;

import java.sql.SQLException;

public class DemoRunner {

	public static void main(String[] args) throws SQLException {
		DatabaseManager.createConnection();
		long startTime = System.currentTimeMillis();
		for (int i = 0; i <= 10000; i++) {
			DatabaseManager.createConnection();
			DatabaseManager.createConnection();
			DatabaseManager.createConnection();
			DatabaseManager.createConnection();
		}

		long endTime = System.currentTimeMillis();
		System.out.println("duration " + (endTime - startTime) + " ms");
	}

}
