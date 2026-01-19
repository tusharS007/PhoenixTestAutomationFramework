package com.api.tests;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.api.utils.DateTimeUtil;
import com.api.utils.SpecUtil;

import io.restassured.module.jsv.JsonSchemaValidator;

public class CreateJobAPITest {

	//Create the CreateJobPayload Object

	@Test
	public void createJobAPITest() {
		
		Customer customer = new Customer("tushar", "shelar", "9321075789", "", "tds@gmail.com", "");
		CustomerAddress customerAddress = new CustomerAddress("A 201", "Alliance", "Karve Nager", "Phoenix", "Pune", "401101", "India", "Maharashtra");
		CustomerProduct customerProduct = new CustomerProduct(DateTimeUtil.getTimeWithDaysAgo(10), "87988284085843", "87988284085843", "87988284085843", DateTimeUtil.getTimeWithDaysAgo(10), 1, 2);
		Problems problems = new Problems(1, "Display Issue");
		List<Problems> problemsList = new ArrayList<Problems>();
		problemsList.add(problems);
		CreateJobPayload createJobPayload = new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct, problemsList);
		
		
		given()
		.spec(SpecUtil.requestSpecificationWithAuth(Role.FD, createJobPayload))
		.when()
		.post("/job/create")
		.then()
		.spec(SpecUtil.responseSpec_OK())
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema\\CreateJobAPIResponseSchema.json"))
		.body("message", Matchers.equalTo("Job created successfully. "))
		.body("data.mst_service_location_id", Matchers.equalTo(1))
		.body("data.job_number", Matchers.startsWith("JOB_"));

	}
}
