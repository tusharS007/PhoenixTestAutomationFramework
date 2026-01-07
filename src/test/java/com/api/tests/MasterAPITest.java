package com.api.tests;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import io.restassured.module.jsv.JsonSchemaValidator;

import static com.api.constant.Role.*;
import static com.api.utils.AuthTokenProvider.*;

import static com.api.utils.ConfigManager.*;

import static io.restassured.RestAssured.*;

public class MasterAPITest {
	
	@Test
	public void masterAPITest() {
		
		given()
		.baseUri(getProperty("BASE_URI"))
		.and()
		.headers("Authorization",getToken(FD))
		.and()
		.contentType("") //empty content type 
		.log().all()
		.when()
		.post("master") // whenerver we make post request default content-type application/url- form encoded 
		.then()
		.log().all()
		.statusCode(200)
		.time(lessThan(1000L))
		.body("message",equalTo("Success"))
		.body("data", notNullValue())
		.body("data",hasKey("mst_oem"))
		.body("data",hasKey("mst_model"))
		.body("$",hasKey("message"))
		.body("$",hasKey("data"))
		.body("data.mst_oem.size()", equalTo(2))
		.body("data.mst_model.size()", greaterThan(2))
		.body("data.mst_oem.id", everyItem(notNullValue()))
		.body("data.mst_oem.name", everyItem(notNullValue()))
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema\\MasterAPIResponseSchema.json"));
	}
	
	@Test
	public void invalidTokenMasterAPITest() {
		
		given()
		.baseUri(getProperty("BASE_URI"))
		.and()
		.headers("Authorization","")
		.and()
		.contentType("") //empty content type 
		.log().all()
		.when()
		.post("master") // whenerver we make post request default content-type application/url- form encoded 
		.then()
		.log().all()
		.statusCode(401);
		
	}

}
