package com.api.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

//Write a Program to read the Properties file from src/test/resources/config/config.properties

	// Create Object of property class
	private static Properties prop = new Properties(); // created static object, only one memory allocation will happen

	private static String path = "config/config.properties";
	private static String env;

	private ConfigManager() {
		// to apply restriction we created private constructor so no object will be
		// creaed outside the class.
	}

	static {

		// if we forget to pass env value in cmd/git by default it will take 'qa' env
		env = System.getProperty("env", "qa");

		env = env.toLowerCase().trim();
		switch (env) {
		case "dev" -> path = "config/config.dev.properties";

		case "qa" -> path = "config/config.qa.properties";

		case "uat" -> path = "config/config.uat.properties";

		default -> path = "config/config.qa.properties";
		}

		// Operation of loading the properties file in the memory
		// static block it will executed once during class loading time
		// we are ensuring object created only once so that code become memory efficient

		// Using InputStram the code is more readable
		InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);

		if (input == null) {
			throw new RuntimeException("Cannot find the file at the path: " + path);
		}

		try {
			prop.load(input);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getProperty(String key) {

		return prop.getProperty("BASE_URI");
	}

}
