package com.api.tests;

import static com.api.utils.SpecUtil.responseSpec_OK;
import static com.api.utils.SpecUtil.responseSpec_TEXT;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.notNullValue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.services.MasterService;

public class MasterAPITest {
	
	MasterService masterService;
	
	@BeforeMethod(description="Instantiating the Master Service Object")
	public void setup() {
		masterService = new MasterService();
	}
	
	@Test(description = "Verify if master API response is shown correctly", groups= {"api","smoke","regression"})
	public void masterAPITest() {
		
		masterService.master(Role.FD)// whenerver we make post request default content-type application/url- form encoded 
		.then()
		.spec(responseSpec_OK())
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
		.body(matchesJsonSchemaInClasspath("response-schema/MasterAPIResponseSchema.json"));
	}
	
	//Negative test
	@Test(description = "Verify if master API is giving correct status code for invalid token", groups= {"api","negative","smoke","regression"})
	public void invalidTokenMasterAPITest() {
		
		masterService.master() // whenerver we make post request default content-type application/url- form encoded 
		.then()
		.spec(responseSpec_TEXT(401));
		
	}

}
