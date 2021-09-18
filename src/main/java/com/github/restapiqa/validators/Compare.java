package com.github.restapiqa.validators;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Compare {
	
	public static boolean compareResponse(String responseBody1, String responseBody2) {
		
		boolean isEqual = false;
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode1 = null;
		JsonNode jsonNode2 = null;
		
		try {
			jsonNode1 = objectMapper.readTree(responseBody1);
			jsonNode2 = objectMapper.readTree(responseBody2);
			if(jsonNode1.equals(jsonNode2)) {
				isEqual = true;
			}
		} catch (Exception e) {
			System.out.println("Error while reading or comparing json" + e.getMessage());
		}
		
		return isEqual;
	
	}

	public static void printResult(String url1, String url2, boolean result) {
		if(result) {
			System.out.println(url1 + " equals " + url2);
		}else {
			System.out.println(url1 + " not equals " + url2);
		}
	}
	
}
