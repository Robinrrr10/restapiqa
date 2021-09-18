package com.github.restapiqa.clients;


import java.util.logging.Logger;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Client {
	
	Logger log = Logger.getGlobal();

	public Response callApi(String url) {
		Response response = null;
		//log.info("Request Url: " + url);
		try { response = RestAssured.get(url); } catch (Exception e) {System.out.println("Error while calling the API: " + e.getMessage());};
		//log.info("Response code: " + response.statusCode());
		//log.info("Response body: " + response.body().asString());
		return response;
	}
	
}
