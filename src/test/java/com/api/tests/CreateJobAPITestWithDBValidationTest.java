package com.api.tests;

import static com.api.utils.SpecUtil.requestSpecificationWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constant.Model;
import com.api.constant.OEM;
import com.api.constant.Platform;
import com.api.constant.Problem;
import com.api.constant.Product;
import com.api.constant.Role;
import com.api.constant.ServiceLocation;
import com.api.constant.Warranty_Status;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.api.utils.DateTimeUtil;
import com.database.dao.CustomerAddressDao;
import com.database.dao.CustomerDao;
import com.database.dao.CustomerProductDao;
import com.database.dao.JobHeadDao;
import com.database.dao.MapJobProblemDao;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import com.database.model.CustomerProductDBModel;
import com.database.model.JobHeadModel;
import com.database.model.MapJobProblemModel;

import io.restassured.response.Response;

public class CreateJobAPITestWithDBValidationTest {

	private CreateJobPayload createJobPayload;
	private Customer customer;
	private CustomerAddress customerAddress;
	private CustomerProduct customerProduct;
	Problems problems;
	@BeforeMethod(description = "Creating createjob API request payload")
	public void setup() {
		customer = new Customer("tushar", "shelar", "9321075789", "", "tds@gmail.com", "");
		customerAddress = new CustomerAddress("A 201", "Alliance", "Karve Nager", "Phoenix", "Pune", "401101", "India", "Maharashtra");
		customerProduct = new CustomerProduct(DateTimeUtil.getTimeWithDaysAgo(10), "75118284083258", "75118284083258", "75118284083258", DateTimeUtil.getTimeWithDaysAgo(10), Product.NEXUS_2.getCode(), Model.NEXUS_2_BLUE.getCode());
		problems = new Problems(Problem.OVERHEATONG.getCode(), "Display Issue");
		
		List<Problems> problemsList = new ArrayList<Problems>();
		problemsList.add(problems);
		createJobPayload = new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode(),
				Platform.FRONT_DESK.getCode(), Warranty_Status.IN_WARRANTY.getCode(), OEM.GOOGLE.getCode(), customer,
				customerAddress, customerProduct, problemsList);
	}
	
	@Test(description = "Verify if create job API is able to create Inwarranty job", groups= {"api","smoke","regression"})
	public void createJobAPITest() {
						
		Response response = given()
		.spec(requestSpecificationWithAuth(Role.FD, createJobPayload))
		.when()
		.post("/job/create")
		.then()
		.spec(responseSpec_OK())
		.body(matchesJsonSchemaInClasspath("response-schema\\CreateJobAPIResponseSchema.json"))
		.body("message", Matchers.equalTo("Job created successfully. "))
		.body("data.mst_service_location_id", Matchers.equalTo(1))
		.body("data.job_number", Matchers.startsWith("JOB_"))
		.extract().response();
		
		int customerId=response.then().extract().body().jsonPath().getInt("data.tr_customer_id");
		
		CustomerDBModel customerDataFromDB = CustomerDao.getCustomerInfo(customerId);
				
		Assert.assertEquals(customer.first_name(), customerDataFromDB.first_name());
		Assert.assertEquals(customer.last_name(), customerDataFromDB.last_name());
		Assert.assertEquals(customer.mobile_number(), customerDataFromDB.mobile_number());
		Assert.assertEquals(customer.mobile_number_alt(), customerDataFromDB.mobile_number_alt());
		Assert.assertEquals(customer.email_id(), customerDataFromDB.email_id());
		Assert.assertEquals(customer.email_id_alt(), customerDataFromDB.email_id_alt());

		CustomerAddressDBModel customerAddressFromDB = CustomerAddressDao.getCustomerAddressData(customerDataFromDB.tr_customer_address_id());
		
		Assert.assertEquals(customerAddressFromDB.flat_number(), customerAddress.flat_number());
		Assert.assertEquals(customerAddressFromDB.apartment_name(), customerAddress.apartment_name());
		Assert.assertEquals(customerAddressFromDB.area(), customerAddress.area());
		Assert.assertEquals(customerAddressFromDB.landmark(), customerAddress.landmark());
		Assert.assertEquals(customerAddressFromDB.state(), customerAddress.state());
		Assert.assertEquals(customerAddressFromDB.country(), customerAddress.country());
		Assert.assertEquals(customerAddressFromDB.pincode(), customerAddress.pincode());
	  
		int productId=response.then().extract().body().jsonPath().getInt("data.tr_customer_product_id");
		
		CustomerProductDBModel customerProductFromDB = CustomerProductDao.getProductInfoFromDB(productId);
		Assert.assertEquals(customerProductFromDB.mst_model_id(), customerProduct.mst_model_id());
		Assert.assertEquals(customerProductFromDB.dop(), customerProduct.dop());
		Assert.assertEquals(customerProductFromDB.popurl(), customerProduct.popurl());
		Assert.assertEquals(customerProductFromDB.imei2(), customerProduct.imei2());
		Assert.assertEquals(customerProductFromDB.imei1(), customerProduct.imei1());
		Assert.assertEquals(customerProductFromDB.serial_number(), customerProduct.serial_number());
		
		int tr_job_head_id = response.body().jsonPath().getInt("data.id");
		MapJobProblemModel jobDataFromDB = MapJobProblemDao.getProblemDetails(tr_job_head_id);
		Assert.assertEquals(jobDataFromDB.id(), createJobPayload.problems().get(0).id() );
		Assert.assertEquals(jobDataFromDB.remark(), createJobPayload.problems().get(0).remark());
	
		JobHeadModel jobHeadDataFromDB= JobHeadDao.getDataFromJobHead(customerId);
		Assert.assertEquals(jobHeadDataFromDB.mst_oem_id(), createJobPayload.mst_oem_id());
		Assert.assertEquals(jobHeadDataFromDB.mst_service_location_id(), createJobPayload.mst_service_location_id());
		Assert.assertEquals(jobHeadDataFromDB.mst_warrenty_status_id(), createJobPayload.mst_warrenty_status_id());
		Assert.assertEquals(jobHeadDataFromDB.mst_platform_id(), createJobPayload.mst_platform_id());
	}
}
