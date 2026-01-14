package com.api.tests;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.pojo.CreateJobPayload;
import com.api.pojo.Customer;
import com.api.pojo.CustomerAddress;
import com.api.pojo.CustomerProduct;
import com.api.pojo.Problems;
import com.api.utils.SpecUtil;

public class CreateJobAPITest {

	//Create the CreateJobPayload Object

	@Test
	public void createJobAPITest() {
		
		Customer customer = new Customer("tushar", "shelar", "9321075789", "", "tds@gmail.com", "");
		CustomerAddress customerAddress = new CustomerAddress("A 201", "Alliance", "Karve Nager", "Phoenix", "Pune", "401101", "India", "Maharashtra");
		CustomerProduct customerProduct = new CustomerProduct("2025-09-30T18:30:00.000Z", "21088284085843", "21088284085843", "21088284085843", "2025-09-30T18:30:00.000Z", 1, 2);
		Problems problems = new Problems(1, "Display Issue");
		Problems[] problemsArray = new Problems[1];
		problemsArray[0]=problems;
		CreateJobPayload createJobPayload = new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct, problemsArray);
		
		
		given()
		.spec(SpecUtil.requestSpecificationWithAuth(Role.FD, createJobPayload))
		.when()
		.post("/job/create")
		.then()
		.spec(SpecUtil.responseSpec_OK());
		

	}
}
