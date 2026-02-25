package com.api.utils;

import static com.api.utils.ConfigManager.getProperty;

import org.hamcrest.Matchers;

import com.api.constant.Role;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecUtil {
	
	//GET - DELETE Without body
	public static RequestSpecification requestSpec() {
		// To take care of the common request sections (method) in test class
		RequestSpecification requestSpecification = new RequestSpecBuilder()
		.setBaseUri(getProperty("BASE_URI"))
		.setContentType(ContentType.JSON)
		.setAccept(ContentType.JSON)
		.log(LogDetail.URI)
		.log(LogDetail.METHOD)
		.log(LogDetail.HEADERS)
		.log(LogDetail.BODY)
		.build();
		
		return requestSpecification;
	}

	// POST - PUT - PATCH (BODY) 
	public static RequestSpecification requestSpec(Object payLoad) {
		// Example of Mehod overloading here to pass body we have added the parameter in method keeping the name is same
		RequestSpecification requestSpecification = new RequestSpecBuilder()
		.setBaseUri(getProperty("BASE_URI"))
		.setContentType(ContentType.JSON)
		.setAccept(ContentType.JSON)
		.setBody(payLoad)
		.log(LogDetail.URI)
		.log(LogDetail.METHOD)
		.log(LogDetail.HEADERS)
		.log(LogDetail.BODY)
		.build();
		
		return requestSpecification;
	}
	
	public static RequestSpecification requestSpecificationWithAuth(Role role) {
		RequestSpecification requestSpecification = new RequestSpecBuilder()
				.setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON)
				.setAccept(ContentType.JSON)
				.addHeader("Authorization", AuthTokenProvider.getToken(role))
				.log(LogDetail.URI)
				.log(LogDetail.METHOD)
				.log(LogDetail.HEADERS)
				.log(LogDetail.BODY)
				.build();
				
		return requestSpecification;
	}
	
	public static RequestSpecification requestSpecificationWithAuth(Role role, Object payLoad) {
		// Example of Mehod overloading here to pass body we have added the parameter in method keeping the name is same
		RequestSpecification requestSpecification = new RequestSpecBuilder()
		.setBaseUri(getProperty("BASE_URI"))
		.setContentType(ContentType.JSON)
		.setAccept(ContentType.JSON)
		.addHeader("Authorization", AuthTokenProvider.getToken(role))
		.setBody(payLoad)
		.log(LogDetail.URI)
		.log(LogDetail.METHOD)
		.log(LogDetail.HEADERS)
		.log(LogDetail.BODY)
		.build();
		
		return requestSpecification;
	}
	
	public static ResponseSpecification responseSpec_OK() {
		
	  ResponseSpecification responseSpecification = new ResponseSpecBuilder()
		.expectContentType(ContentType.JSON)
		.expectStatusCode(200)
		.expectResponseTime(Matchers.lessThan(2000L))
		.log(LogDetail.ALL)
		.build();
		
	  return responseSpecification;
	}
	
	public static ResponseSpecification responseSpec_JSON(int statusCode) {
		
		  ResponseSpecification responseSpecification = new ResponseSpecBuilder()
			.expectContentType(ContentType.JSON)
			.expectStatusCode(statusCode)
			.expectResponseTime(Matchers.lessThan(1000L))
			.log(LogDetail.ALL)
			.build();
			
		  return responseSpecification;
		}
	
	public static ResponseSpecification responseSpec_TEXT(int statusCode) {
		
		  ResponseSpecification responseSpecification = new ResponseSpecBuilder()
			.expectStatusCode(statusCode)
			.expectResponseTime(Matchers.lessThan(1000L))
			.log(LogDetail.ALL)
			.build();
			
		  return responseSpecification;
		}
	

}
