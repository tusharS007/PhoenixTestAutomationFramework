package com.api.utils;

import static com.api.utils.ConfigManager.getProperty;

import org.hamcrest.Matchers;

import com.api.constant.Role;
import com.api.filters.SensitiveDataFilter;

import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecUtil {
	
	//GET - DELETE Without body
	@Step("Setting up the BaseURI, Content Type as Application/JSON and Attaching the SensitiveData Filter")
	public static RequestSpecification requestSpec() {
		// To take care of the common request sections (method) in test class
		RequestSpecification requestSpecification = new RequestSpecBuilder()
		.setBaseUri(getProperty("BASE_URI"))
		.setContentType(ContentType.JSON)
		.setAccept(ContentType.JSON)
		.addFilter(new SensitiveDataFilter())
		.build();
		
		return requestSpecification;
	}

	// POST - PUT - PATCH (BODY) 
	@Step("Setting up the BaseURI, Content Type as Application/JSON and Attaching the SensitiveData Filter")
	public static RequestSpecification requestSpec(Object payLoad) {
		// Example of Mehod overloading here to pass body we have added the parameter in method keeping the name is same
		RequestSpecification requestSpecification = new RequestSpecBuilder()
		.setBaseUri(getProperty("BASE_URI"))
		.setContentType(ContentType.JSON)
		.setAccept(ContentType.JSON)
		.setBody(payLoad)
		.addFilter(new SensitiveDataFilter())
		.build();
		
		return requestSpecification;
	}
	
	@Step("Setting up the BaseURI, Content Type as Application/JSON and Attaching the SensitiveData Filter for a role")
	public static RequestSpecification requestSpecificationWithAuth(Role role) {
		RequestSpecification requestSpecification = new RequestSpecBuilder()
				.setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON)
				.setAccept(ContentType.JSON)
				.addHeader("Authorization", AuthTokenProvider.getToken(role))
				.addFilter(new SensitiveDataFilter())
				.build();
				
		return requestSpecification;
	}
	
	@Step("Setting up the BaseURI, Content Type as Application/JSON and Attaching the SensitiveData Filter for a role and attaching payload")
	public static RequestSpecification requestSpecificationWithAuth(Role role, Object payLoad) {
		// Example of Mehod overloading here to pass body we have added the parameter in method keeping the name is same
		RequestSpecification requestSpecification = new RequestSpecBuilder()
		.setBaseUri(getProperty("BASE_URI"))
		.setContentType(ContentType.JSON)
		.setAccept(ContentType.JSON)
		.addHeader("Authorization", AuthTokenProvider.getToken(role))
		.setBody(payLoad)
		.addFilter(new SensitiveDataFilter())
		.build();
		
		return requestSpecification;
	}
	
	@Step("Expecting the response to have content type as Application/json, status 200 and Response TIme less than 1000 ms")
	public static ResponseSpecification responseSpec_OK() {
		
	  ResponseSpecification responseSpecification = new ResponseSpecBuilder()
		.expectContentType(ContentType.JSON)
		.expectStatusCode(200)
		.expectResponseTime(Matchers.lessThan(2000L))
		.build();
		
	  return responseSpecification;
	}
	
	@Step("Expecting the response to have content type as Application/json, Response TIme less than 1000 ms and status code")
	public static ResponseSpecification responseSpec_JSON(int statusCode) {
		
		  ResponseSpecification responseSpecification = new ResponseSpecBuilder()
			.expectContentType(ContentType.JSON)
			.expectStatusCode(statusCode)
			.expectResponseTime(Matchers.lessThan(1000L))
			.build();
			
		  return responseSpecification;
		}
	

	@Step("Expecting the response to have content type as Text, Response TIme less than 1000 ms and status code")
	public static ResponseSpecification responseSpec_TEXT(int statusCode) {
		
		  ResponseSpecification responseSpecification = new ResponseSpecBuilder()
			.expectStatusCode(statusCode)
			.expectResponseTime(Matchers.lessThan(1000L))
			.build();
			
		  return responseSpecification;
		}

}
