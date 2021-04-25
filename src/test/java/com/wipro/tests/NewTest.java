package com.wipro.tests;


import org.testng.Assert;
import org.testng.annotations.Test;
import com.wipro.pages.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;


public class NewTest {
	
	static String place_id;
	 
	@Test(priority=0)
	public void ValidatePostRequest() {
		
		// First mention the base url
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		// given with query & path parameters, request payload,headers
		String response = RestAssured.given().queryParam("key", "qaclick123").header("content-type","application/json")
		.body(Payload.inputJson()).
		// when with http request type and resource
		when().post("/maps/api/place/add/json")
		//then output assert
		.then().assertThat().statusCode(200)
		//extract the reponse payload
		.extract().response().asString();
		
		
		//Storing the output json
		JsonPath js = new JsonPath(response);
		place_id=js.get("place_id");
		
	}
	
	
	@Test(priority=1)
	public void ValidateGetRequest() {
		
		System.out.println("place_id ---> "+place_id);
		RestAssured.baseURI = "https://rahulshettyacademy.com/";
		RestAssured.given().header("content-type","application/json").queryParam("key", "qaclick123").queryParam("place_id", place_id)
		.when().get("maps/api/place/get/json")
		.then().assertThat().statusCode(200);
	}
	
	@Test(priority=2)
	public void ValidateUpdatetRequest() {
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String place_new= place_id;
		String address= "Sonagiri Bhopal 21";
		String response=
		RestAssured.given().header("content-type","application/json").queryParam("key", "qaclick123").queryParam("place_id", place_id)
		.body("{\r\n" + 
				"\"place_id\":\""+place_new+"\",\r\n" + 
				"\"address\":\""+address+"\",\r\n" + 
				"\"key\":\"qaclick123\"\r\n" + 
				"}\r\n" + 
				" ")
		.when().put("/maps/api/place/update/json")
		.then().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js = new JsonPath(response);
		String msg = js.get("msg");
		
		Assert.assertEquals(msg,"Address successfully updated");
	}
	

	@Test(priority=3)
	public void validateDeletRequest() {
		RestAssured.baseURI = "https://rahulshettyacademy.com/";
		
		RestAssured.given().header("content-type","application/json").queryParam("key", "qaclick123").queryParam("place_id", place_id)
		.body("{\r\n" + 
				"    \"place_id\":\""+place_id+"\"\r\n" + 
				"}\r\n" + 
				"")
		.when().delete("maps/api/place/delete/json")
		.then().assertThat().statusCode(200);
		
		System.out.println("place_id ---> "+place_id);
		RestAssured.given().header("content-type","application/json").queryParam("key", "qaclick123").queryParam("place_id", place_id)
		.when().get("maps/api/place/get/json")
		.then().assertThat().statusCode(404);
	}

}
