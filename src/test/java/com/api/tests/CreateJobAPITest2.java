	package com.api.tests;

import static com.api.utils.SpecUtil.requestSpecificationWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.request.model.CreateJobPayload;
import com.api.utils.FakerDataFGenerator;

public class CreateJobAPITest2 {

	private CreateJobPayload createJobPayload;
	private final static int count =5;
	@BeforeMethod(description = "Creating createjob API request payload")
	public void setup() {
	    createJobPayload = FakerDataFGenerator.generateFakeCreateJobData();
	}
	
	@Test(description = "Verify if create job API is able to create Inwarranty job", groups= {"api","smoke","regression"},invocationCount = count)
	public void createJobAPITest() {
						
		given()
		.spec(requestSpecificationWithAuth(Role.FD, createJobPayload))
		.when()
		.post("/job/create")
		.then()
		.spec(responseSpec_OK())
		.body(matchesJsonSchemaInClasspath("response-schema\\CreateJobAPIResponseSchema.json"))
		.body("message", Matchers.equalTo("Job created successfully. "))
		.body("data.mst_service_location_id", Matchers.equalTo(1))
		.body("data.job_number", Matchers.startsWith("JOB_"));

	}
}
