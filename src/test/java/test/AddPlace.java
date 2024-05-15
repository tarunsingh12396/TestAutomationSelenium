package test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
//import payload.RequestPayload;
import pojo.Location;
import pojo.PlaceData;

public class AddPlace {

	String placeid;
	Workbook book;

	@BeforeMethod
	public void setup() {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
	}

	@Test(priority = 0, description = "Getting existing place data from server", invocationCount = 1, groups = "Smoke")
	public void creategoogleplace() {

		PlaceData d = new PlaceData();

		Location L = new Location();

		L.setLat(-38.383494);
		L.setLng(33.427362);

		d.setLocation(L);
		d.setAccuracy(50);
		d.setName("TestAutomation");
		d.setPhone_number("8524663546");
		d.setAddress("Noida");

		List<String> list = new ArrayList<String>();
		list.add("shoe park");
		list.add("shoe");

		d.setTypes(list);

		d.setWebsite("http://google.com");
		d.setLanguage("French-IN");

		RestAssured.baseURI = "https://rahulshettyacademy.com";

		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(d).when().post("maps/api/place/add/json").then().log().all().assertThat().statusCode(200)
				.header("Server", "Apache/2.4.52 (Ubuntu)").body("scope", equalTo("APP")).extract().response()
				.asString();

		JsonPath js = new JsonPath(response);
		placeid = js.getString("place_id");

		System.out.println(placeid);

		String statusValue = js.getString("status");
		assertEquals("OK", statusValue);
	}

	@Test(priority = 2, description = "Creating a place data on server", invocationCount = 1, groups = "Smoke")
	public void getgoogleplacedata() {

		RestAssured.baseURI = "https://rahulshettyacademy.com";

		String response = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeid).when()
				.get("maps/api/place/get/json").then().log().all().assertThat().statusCode(200).extract().response()
				.asString();

		JsonPath js1 = new JsonPath(response);
		String add = js1.get("address");

		System.out.println(add);

		assertEquals(add, "Delhi NCR");

	}

	@Test(priority = 1)
	public void updateaddress() {

		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body("{\r\n" + "\"place_id\":\"" + placeid + "\",\r\n" + "\"address\":\"Delhi NCR\",\r\n"
						+ "\"key\":\"qaclick123\"\r\n" + "}")
				.when().put("maps/api/place/update/json").then().log().all().assertThat().statusCode(200)
				.header("Server", "Apache/2.4.52 (Ubuntu)").body("msg", equalTo("Address successfully updated"));
		// .extract().response().asString();

	}

	@DataProvider(name = "userdata")
	public Object[][] getdata(String sheetName) {

		try {
			FileInputStream file = new FileInputStream(
					"C:\\Users\\rudra\\Downloads\\TestAutomation\\TestData1\\name address.xlsx");
			try {
				book = WorkbookFactory.create(file);
			} catch (EncryptedDocumentException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		Sheet sheet = book.getSheet(sheetName); // sheet name pass krdo bus kyu ki excel mai bhut sheet hogi toh apko
												// bus sheet name pass krna h

		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];

		for (int i = 0; i < sheet.getLastRowNum(); i++) {

			for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {

				data[i][j] = sheet.getRow(i + 1).getCell(j).toString();

			}
		}

		return data;

	}

}
