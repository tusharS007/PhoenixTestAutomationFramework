package com.api.tests;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.request.model.Detail;
import com.api.services.DashboardService;
import com.api.utils.SpecUtil;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Listeners(com.listeners.APITestListener.class)
@Epic("Job Management")
@Feature("Job Details")
public class DetailsAPITest {

	private DashboardService dashboardService;
	private Detail detailPayload;

	@BeforeMethod(description = "Instantiated the dashboard service and creating detail payload")
	public void setup() {
		dashboardService = new DashboardService();
		detailPayload = new Detail("created_today");
	}

	@Story("Job details shown correctly")
	@Description("Verify if FD user is able to login via API")
	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Verify if Detail API is working properly", groups = { "api", "smoke",
			"e2e" }, retryAnalyzer = com.api.retry.RetryAnalyzer.class)
	public void detailAPITest() {

		dashboardService.details(Role.FD, detailPayload)
		.then()
		.spec(SpecUtil.responseSpec_OK())
		.body("message", Matchers.equalTo("Success"));
	}

}
