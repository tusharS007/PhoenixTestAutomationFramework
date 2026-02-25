package com.api.utils;

import static com.api.constant.Role.ENG;
import static com.api.constant.Role.FD;
import static com.api.constant.Role.QC;
import static com.api.constant.Role.SUP;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.api.constant.Role;
import com.api.request.model.UserCredetials;

import io.restassured.http.ContentType;

public class AuthTokenProvider {

	private static Map<Role,String> tokenCache = new ConcurrentHashMap<Role, String>();
	
	private AuthTokenProvider() {

		//To restrict object creation outside of the claas we created private constructor.
	}

	public static String getToken(Role role) {

		if(tokenCache.containsKey(role)) {
			return tokenCache.get(role);
		}
		
		UserCredetials userDetails = null;

		if (role == FD) {
			userDetails = new UserCredetials("iamfd", "password");
		}

		else if (role == SUP) {
			userDetails = new UserCredetials("iamsup", "password");
		}

		else if (role == ENG) {
			userDetails = new UserCredetials("iameng", "password");
		}

		else if (role == QC) {
			userDetails = new UserCredetials("iamqc", "password");
		}

		String token = given().baseUri(ConfigManager.getProperty("BASE_URI")).contentType(ContentType.JSON)
				.body(userDetails)
				.when().post("login")
				.then().log().ifValidationFails().statusCode(200)
				.body("message", equalTo("Success")).extract().body().jsonPath().getString("data.token");

		tokenCache.put(role, token);
		return token;
	}

}
