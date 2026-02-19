	package com.api.tests;

import static com.api.utils.SpecUtil.requestSpecificationWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.utils.FakerDataFGenerator;
import com.database.dao.CustomerAddressDao;
import com.database.dao.CustomerDao;
import com.database.dao.JobHeadDao;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import com.database.model.JobHeadModel;

public class CreateJobAPITestWithFakeData {

	private CreateJobPayload createJobPayload;
	private final static int count =1;
	@BeforeMethod(description = "Creating createjob API request payload")
	public void setup() {
	    createJobPayload = FakerDataFGenerator.generateFakeCreateJobData();
	}
	
	@Test(description = "Verify if create job API is able to create Inwarranty job", groups= {"api","smoke","regression"},invocationCount = count)
	public void createJobAPITest() {
						
		int customerId = given()
		.spec(requestSpecificationWithAuth(Role.FD, createJobPayload))
		.when()
		.post("/job/create")
		.then()
		.spec(responseSpec_OK())
		.body(matchesJsonSchemaInClasspath("response-schema\\CreateJobAPIResponseSchema.json"))
		.body("message", Matchers.equalTo("Job created successfully. "))
		.body("data.mst_service_location_id", Matchers.equalTo(1))
		.body("data.job_number", Matchers.startsWith("JOB_"))
		.extract().body().jsonPath().getInt("data.tr_customer_id");
		
		Customer expectedCustomerData= createJobPayload.customer();
		CustomerDBModel actualCustomerDataInDB = CustomerDao.getCustomerInfo(customerId);
	    
		Assert.assertEquals(expectedCustomerData.first_name(), actualCustomerDataInDB.first_name());
		Assert.assertEquals(expectedCustomerData.last_name(), actualCustomerDataInDB.last_name());
		Assert.assertEquals(expectedCustomerData.mobile_number(), actualCustomerDataInDB.mobile_number());
		Assert.assertEquals(expectedCustomerData.mobile_number_alt(), actualCustomerDataInDB.mobile_number_alt());
		Assert.assertEquals(expectedCustomerData.email_id(), actualCustomerDataInDB.email_id());
		Assert.assertEquals(expectedCustomerData.email_id_alt(), actualCustomerDataInDB.email_id_alt());


		CustomerAddressDBModel customerAddressFromDB = CustomerAddressDao.getCustomerAddressData(actualCustomerDataInDB.tr_customer_address_id());
		
		Assert.assertEquals(customerAddressFromDB.flat_number(), createJobPayload.customer_address().flat_number());
		Assert.assertEquals(customerAddressFromDB.apartment_name(), createJobPayload.customer_address().apartment_name());
		Assert.assertEquals(customerAddressFromDB.area(), createJobPayload.customer_address().area());
		Assert.assertEquals(customerAddressFromDB.landmark(), createJobPayload.customer_address().landmark());
		Assert.assertEquals(customerAddressFromDB.state(), createJobPayload.customer_address().state());
		Assert.assertEquals(customerAddressFromDB.country(), createJobPayload.customer_address().country());
		Assert.assertEquals(customerAddressFromDB.pincode(), createJobPayload.customer_address().pincode());
	
		JobHeadModel jobHeadDataFromDB= JobHeadDao.getDataFromJobHead(customerId);
		Assert.assertEquals(jobHeadDataFromDB.mst_oem_id(), createJobPayload.mst_oem_id());
		Assert.assertEquals(jobHeadDataFromDB.mst_service_location_id(), createJobPayload.mst_service_location_id());
		Assert.assertEquals(jobHeadDataFromDB.mst_warrenty_status_id(), createJobPayload.mst_warrenty_status_id());
		Assert.assertEquals(jobHeadDataFromDB.mst_platform_id(), createJobPayload.mst_platform_id());
	
		
	}
}
