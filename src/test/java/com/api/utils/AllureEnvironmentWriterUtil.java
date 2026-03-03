package com.api.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AllureEnvironmentWriterUtil {
	private static final Logger LOGGER = LogManager.getLogger(AllureEnvironmentWriterUtil.class);

	// environment-properties
	// Create properties file
	public static void createEnvironmentPropertiesFile() {

		String folderPath = "target/allure-results";
		File file = new File(folderPath);
		file.mkdirs();

		Properties prop = new Properties();
		// prop.setProperty("Name", "Tushar");
		prop.setProperty("Project_Name", "Phoenix Test Automation Framework");
		prop.setProperty("Env", ConfigManager.env);
		prop.setProperty("BASE_URI", ConfigManager.getProperty("BASE_URI"));
		prop.setProperty("Operating_System", System.getProperty("os.version"));
		prop.setProperty("Operating_System_Version", System.getProperty("os.name"));
		prop.setProperty("Java_Version", System.getProperty("java.version"));
		FileWriter fw;
		try {
			fw = new FileWriter(folderPath + "/environment.properties");
			prop.store(fw, "My Properties File");
			LOGGER.info("Created the environment,properties file at {}", folderPath);

		} catch (IOException e) {
			LOGGER.error("Unable to create the environment.properties file", e);
			e.printStackTrace();
		}

	}
}
