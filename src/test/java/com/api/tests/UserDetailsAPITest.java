package com.api.tests;

import static com.api.constant.Role.FD;
import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.testng.annotations.Test;

import com.api.utils.SpecUtil;

import io.restassured.module.jsv.JsonSchemaValidator;

public class UserDetailsAPITest {

	@Test(description = "Verify if userdetails API response is shown correctly", groups= {"api","smoke","regression"})
	public void userDetailsAPITest() throws IOException {
			
		given()
		  .spec(SpecUtil.requestSpecificationWithAuth(FD))
		.when()
		  .get("userdetails")
		  .then()
		  .spec(SpecUtil.responseSpec_OK())
		  .and()
		  .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"));
	}
}
