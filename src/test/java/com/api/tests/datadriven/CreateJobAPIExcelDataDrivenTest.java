package com.api.tests.datadriven;

import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.request.model.CreateJobPayload;
import com.api.services.JobService;

public class CreateJobAPIExcelDataDrivenTest {

	private JobService jobService;

	@BeforeMethod(description = "Instantiated Job Service")
	public void setup() {
		jobService = new JobService();
	}
	
	@Test(description = "Verify if create job API is able to create Inwarranty job", groups= {"api","smoke","datadriven","csv"},
			dataProviderClass = com.dataproviders.DataProviderUtils.class,
			dataProvider = "CreateJobAPIExcelDataProvider")
	public void createJobAPITest(CreateJobPayload createJobPayload) {
						
		jobService.create(Role.FD, createJobPayload)
		.then()
		.spec(responseSpec_OK())
		.body(matchesJsonSchemaInClasspath("response-schema\\CreateJobAPIResponseSchema.json"))
		.body("message", Matchers.equalTo("Job created successfully. "))
		.body("data.mst_service_location_id", Matchers.equalTo(1))
		.body("data.job_number", Matchers.startsWith("JOB_"));

	}
}
