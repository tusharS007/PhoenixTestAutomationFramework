package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonReaderUtil {

	private static final Logger LOGGER = LogManager.getLogger(JsonReaderUtil.class);
	
	public static <T> Iterator<T> loadJson(String fileName, Class<T[]> clazz) {

		LOGGER.info("Reading the JSON from the file {}",fileName);
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
		T[] classArray;
		List<T> list = null;
		// Convert the JSON pbject to Java Object!!! ---> Deserialization
		// Jackson DataBind ----> ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			LOGGER.info("Converting the JSON data to bean class {}",clazz);
			classArray = objectMapper.readValue(is, clazz);
			list = Arrays.asList(classArray);
		} catch (IOException e) {
			LOGGER.error("Cannot read the JSON from the file {}",fileName, e);
			e.printStackTrace();
		}

		return list.iterator();
	}

}
