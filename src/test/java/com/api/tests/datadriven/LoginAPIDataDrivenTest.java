package com.api.tests.datadriven;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.UserCredetials;
import com.dataproviders.api.bean.UserBean;

import static com.api.utils.SpecUtil.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class LoginAPIDataDrivenTest {
	
	@Test(description = "Verify if login API is working for FD user",
			groups= {"api","regression","smoke"},
			dataProviderClass = com.dataproviders.DataProviderUtils.class,
			dataProvider = "LoginAPIDataProvider"			
			)
	public void loginAPITest(UserBean userbean) throws IOException {
		
		given()
		.spec(requestSpec(userbean))
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
