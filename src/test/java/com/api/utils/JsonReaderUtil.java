package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonReaderUtil {

	public static <T> Iterator<T> loadJson(String fileName, Class<T[]> clazz) {

		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
		T[] classArray;
		List<T> list = null;
		// Convert the JSON pbject to Java Object!!! ---> Deserialization
		// Jackson DataBind ----> ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			classArray = objectMapper.readValue(is, clazz);
			list = Arrays.asList(classArray);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return list.iterator();
	}

}
