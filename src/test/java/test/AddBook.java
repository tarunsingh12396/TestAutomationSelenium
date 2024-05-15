package test;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test; // issue 
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import pojo.BookData;

public class AddBook {

	@Test
	public void addNewBook() {

		BookData d = new BookData();

		d.setName("Test");
		d.setIsbn("sdfs");
		d.setAisle("1234");
		d.setAuthor("Rahul");

		RestAssured.baseURI = "https://rahulshettyacademy.com";

		given().log().all().header("Content-Type", "application/json").body("d").when().post("Library/Addbook.php")
		.then().assertThat().statusCode(200).body(JsonSchemaValidator.matchesJsonSchema(
		new File("C:\\Users\\rudra\\Downloads\\TestAutomation\\TestData1\\test.json1.txt")));

	}

}
