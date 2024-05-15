package payload;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;

public class MockTest {

	@Test
	public void ValidateTest() {

		JsonPath js = new JsonPath(GoogleplacePayload.coursePrice());
		int totalcourses = js.getInt("courses.size()");
		System.out.println(totalcourses);

		String titlefirstcourse = js.getString("course[0].title");
		System.out.println(titlefirstcourse);

		for (int i = 0; i < totalcourses; i++) {

			String title = js.getString("courses[" + i + "].title");
			System.out.println(title);

			if (title.equals("RPA")) {

				int copies = js.getInt("courses[" + i + "].copies");
				System.out.println(copies);

			}

			int price = js.getInt("courses[" + i + "].price");
			System.out.println(price);

		}

		int sum = 0;
		for (int j = 0; j < totalcourses; j++) {
			int copies = js.getInt("courses[" + j + "].copies");
			int price = js.getInt("courses[" + j + "].price");

			int totalprice = copies * price;

			sum = sum + totalprice;

		}
		System.out.println(sum);

		int purchaseamount = js.getInt("dashboard.purchaseamount");
		System.out.println(purchaseamount);

		assertEquals(sum, purchaseamount);

	}

}
