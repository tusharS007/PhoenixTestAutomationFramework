package com.api.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.UserCredetials;
import static com.api.utils.SpecUtil.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class LoginAPITest {
	
	private UserCredetials usercredentials;
	
	@BeforeMethod(description = "Create the Payload for the Login API")
	public void setup() {
		
		usercredentials = new UserCredetials("iamfd","password");
	}
	
	@Test(description = "Verify if login API is working for FD user", groups= {"api","regression","smoke"})
	public void loginAPITest() throws IOException {
		
		given()
		.spec(requestSpec(usercredentials))
		.when()
		.post("login")
		.then()
		.spec(responseSpec_OK())
		.and()
		.body("message", equalTo("Success"))
		.and()
		.body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));
	}
}
