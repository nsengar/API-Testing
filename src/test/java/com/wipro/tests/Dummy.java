package com.wipro.tests;


import org.testng.Assert;
import org.testng.annotations.Test;
import com.wipro.pages.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;


public class Dummy {
	
	static String id;
	 
	@Test(priority=0)
	public void ValidatePostRequest() {
		
		// First mention the base url
		RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1";
		// given with query & path parameters, request payload,headers
		String response = RestAssured.given().header("content-type","application/json")
		.body(Payload.dummyInputJson()).
		// when with http request type and resource
		when().post("/create")
		//then output assert
		.then().assertThat().statusCode(200)
		//extract the reponse payload
		.extract().response().asString();
		
		
		//Storing the output json
		JsonPath js = new JsonPath(response);
		id=js.get("data.id").toString();
		
	}
	
	
	@Test(priority=1)
	public void ValidateGetRequest() {
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("id ---> "+id);
		RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1";
		RestAssured.given().header("content-type","application/json"). pathParam("emp_id", id)
		.when().get("/employee/{emp_id}")
		.then().log().all().assertThat().statusCode(200);
	}
	


}
