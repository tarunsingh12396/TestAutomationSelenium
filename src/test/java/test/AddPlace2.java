package test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import payload.RequestPayload;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

public class AddPlace2 {

static	String  placeid;

	@Test(priority=0,enabled=false)
	public void CreateGooglePlace() {
		
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
	String Response=	given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json").body(RequestPayload.placepayload()).
		when().post("maps/api/place/add/json").
		then().assertThat().log().all().statusCode(200).header("Server", "Apache/2.4.52 (Ubuntu)").body("status",equalTo("OK")).extract().response().asString();
		
		JsonPath js = new  JsonPath(Response);
	 placeid =	js.getString("place_id");
	System.out.println(placeid);
			
		
	}
	@Test(priority=1,description="Creating a place data on server",invocationCount = 1 ,groups="Smoke")
	public void getgoogleplacedata2() {
		
	String response1 = 	given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeid).
		when().get("maps/api/place/get/json").
		then().assertThat().log().all().statusCode(200).extract().response().asString();
	
	
	JsonPath js3 = new JsonPath(response1);
String add =	js3.getString("address");

System.out.println(add);


  assertEquals(add, "29, side layout, cohen 09" );
		
		
		
		
		
	}
	
	@Test (priority=2)
	public void updateaddress() {
		
		
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json").body("{\r\n"
				+ "\"place_id\":\""+placeid+"\",\r\n"
				+ "\"address\":\"Delhi NCR\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
			.when().put("maps/api/place/update/json")
			.then().log().all().assertThat().statusCode(200)
			.header("Server", "Apache/2.4.52 (Ubuntu)")
		//   .body("msg",equalTo("Address successfully updated"))			
		.extract().response().asString(); 
			
	}
	    @Test (priority=3)
		public void abc() {
			
	String rah =		given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeid).when().get("maps/api/place/update/json")
			.then().log().all().assertThat().statusCode(200).extract().response().asString();
			
			
			JsonPath jdk = new JsonPath(rah);
	String addnew =		jdk.getString("new address");
			assertEquals("expected address", addnew);
			
		}
	
	
	
	
	
	
	
}
