package com.api.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class SensitiveDataFilter implements Filter {

	Logger LOGGER = LogManager.getLogger(SensitiveDataFilter.class);
	// implement filters

	@Override
	public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec,
			FilterContext ctx) {
		System.out.println("------------- Hello From the Filter!! -------------");
		redactPayload(requestSpec);
		Response response = ctx.next(requestSpec, responseSpec);
		System.out.println("------------- I got the response in filter -------------");
		redactResponseBody(response);
		return response;
	}
	
	private void redactResponseBody(Response response) {
		String responseNody=response.asPrettyString();
		responseNody =responseNody.replaceAll("\"token\"\s*:\s*\"[^\"]+\"", "\"token\":\"[REDACTED]\"");
	    LOGGER.info("RESPONSE PAYLOAD : {} ",responseNody);
		
	}

	public void redactPayload(FilterableRequestSpecification requestSpec) {
		String requestPayload = requestSpec.getBody().toString();
		//
		requestPayload =requestPayload.replaceAll("\"password\"\s*:\s*\"[^\"]+\"", "\"password\":\"[REDACTED]\"");
		 LOGGER.info("REQUEST PAYLOAD : {} ",requestPayload);;
	}
}
