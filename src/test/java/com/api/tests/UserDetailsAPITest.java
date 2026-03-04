package com.api.tests;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.services.UserDetailsService;
import com.api.utils.SpecUtil;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.module.jsv.JsonSchemaValidator;

@Listeners(com.listeners.APITestListener.class)
@Epic("User Management")
@Feature("User Details")
public class UserDetailsAPITest {
 
	private UserDetailsService userDetailsService;
	
	@BeforeMethod(description="Setting up the UserDetailsService Instance")
	public void setup(){
		userDetailsService = new UserDetailsService();
	}
	
	@Story("User Details should be shown")
	@Description("Verify if FD user is able to login via API")
	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Verify if userdetails API response is shown correctly", groups= {"api","smoke","regression"})
	public void userDetailsAPITest() throws IOException {
			
		userDetailsService.userDetails(Role.FD)
		  .then()
		  .spec(SpecUtil.responseSpec_OK())
		  .and()
		  .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"));
	}
}
