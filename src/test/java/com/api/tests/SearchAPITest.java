package com.api.tests;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.request.model.Search;
import com.api.services.JobService;
import com.api.utils.SpecUtil;

@Listeners(com.listeners.APITestListener.class)
public class SearchAPITest {
	
	private JobService jobService;
	private static final String JOB_NUMBER = "JOB_190338";
	private Search searchPayload;
	

	@BeforeMethod(description = "Instantiated the job service and creating search payload")
	public void setup() {
		jobService = new JobService();
		searchPayload = new Search(JOB_NUMBER);
	}

	@Test(description = "Verify if Search API is working properly", groups = { "api", "smoke",
			"e2e" }, retryAnalyzer = com.api.retry.RetryAnalyzer.class)
	public void searchAPITest() {

		jobService.search(Role.FD, searchPayload)
		.then()
		.spec(SpecUtil.responseSpec_OK())
		.body("message", Matchers.equalTo("Success"));
	}
	

}
