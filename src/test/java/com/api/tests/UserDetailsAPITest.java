package com.api.tests;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.services.UserDetailsService;
import com.api.utils.SpecUtil;

import io.restassured.module.jsv.JsonSchemaValidator;

public class UserDetailsAPITest {
 
	private UserDetailsService userDetailsService;
	
	@BeforeMethod(description="Setting up the UserDetailsService Instance")
	public void setup(){
		userDetailsService = new UserDetailsService();
	}
	
	@Test(description = "Verify if userdetails API response is shown correctly", groups= {"api","smoke","regression"})
	public void userDetailsAPITest() throws IOException {
			
		userDetailsService.userDetails(Role.FD)
		  .then()
		  .spec(SpecUtil.responseSpec_OK())
		  .and()
		  .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"));
	}
}
