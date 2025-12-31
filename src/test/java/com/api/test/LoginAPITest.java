package com.api.test;

import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import com.api.pojo.UserCredetials;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

public class LoginAPITest {
	
	@Test
	public void loginAPITest() {
		
		UserCredetials usercredentials = new UserCredetials("iamfd","password");
		
		given()
		.baseUri("http://64.227.160.186:9000/v1")
		.and()
		.contentType(ContentType.JSON)
		.and()
		.accept(ContentType.JSON)
		.and()
		.body(usercredentials)
		.log().uri()
		.log().method()
		.log().headers()
		.log().body()
		.when()
		.post("login")
		.then()
		.log().all()
		.statusCode(200)
		.time(lessThan(2000L))
		.and()
		.body("message", equalTo("Success"))
		.and()
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));
	}
}
