package com.api.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigManagerOld {

//Write a Program to read the Properties file from src/test/resources/config/config.properties

	// Create Object of property class
	private static Properties prop = new Properties(); //created static object, only one memory allocation will happen

	private ConfigManagerOld() {
		//to apply restriction we created private constructor so no object will be creaed outside the class.
	}
	static {
		//Operation of loading the properties file in the memory
		// static block it will executed once during class loading time
		// we are ensuring object created only once so that code become memory efficient
		// File.separator is used to handel / & \ slashes so that it can work on any machine(windows(\),mac and linux(/) etc.)
		File configFile = new File(
				System.getProperty("user.dir") +File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"config"+File.separator+"config.properties");

		FileReader fr;
		try {
			fr = new FileReader(configFile);
			prop.load(fr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getProperty(String key) {

		return prop.getProperty("BASE_URI");
	}

}
