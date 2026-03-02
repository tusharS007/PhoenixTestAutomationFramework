package com.api.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CSVReaderUtil {
	/*
	 * Constructor is private
	 * 
	 * static - static method job: Help me read the CSV file and Map it a Bean
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(CSVReaderUtil.class);
	
	public static <T> Iterator<T> loadCSV(String pathOfCSVFile, Class<T> bean)  {
		LOGGER.info("Loading CSV file from the path {}",pathOfCSVFile);
		InputStream is= Thread.currentThread().getContextClassLoader().getResourceAsStream(pathOfCSVFile);
		InputStreamReader isr = new InputStreamReader(is);
    	CSVReader csvReader = new CSVReader(isr);
	
    	LOGGER.info("Converting the CSV to the bean class {}",bean);
    	//Write the code to Map the CSV to POJO
    	CsvToBean<T> csvToBean= new CsvToBeanBuilder(csvReader)
    			.withType(bean)
    			.withIgnoreEmptyLine(true)
    			.build();
    	
    	List<T> list = csvToBean.parse();
    	return list.iterator();
	
	}
}
