package com.api.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.testng.annotations.Test;

import com.api.request.model.UserCredetials;
import com.api.utils.SpecUtil;

import io.restassured.module.jsv.JsonSchemaValidator;

public class LoginAPITest {
	
	@Test
	public void loginAPITest() throws IOException {
		
		UserCredetials usercredentials = new UserCredetials("iamfd","password");
		
		given()
		.spec(SpecUtil.requestSpec(usercredentials))
		.when()
		.post("login")
		.then()
		.spec(SpecUtil.responseSpec_OK())
		.and()
		.body("message", equalTo("Success"))
		.and()
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));
	}
}
