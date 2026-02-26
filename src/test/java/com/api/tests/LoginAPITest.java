package com.api.tests;

import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.UserCredetials;
import com.api.services.AuthService;

public class LoginAPITest {
	
	private UserCredetials userCredentials;
	private AuthService authService;
	@BeforeMethod(description = "Create the Payload for the Login API")
	public void setup() {
		
		userCredentials = new UserCredetials("iamfd","password");
		authService = new AuthService();
	}
	
	@Test(description = "Verify if login API is working for FD user", groups= {"api","regression","smoke"})
	public void loginAPITest() throws IOException {
		
		authService.login(userCredentials)		
		.then()
		.spec(responseSpec_OK())
		.and()
		.body("message", equalTo("Success"))
		.and()
		.body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));
	}
}
