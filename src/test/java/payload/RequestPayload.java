package payload;

public class RequestPayload {
	
 public static String placepayload (String name,String address) {
	 
	 
return "                 {\r\n"
	 + "				 \"location\": {\r\n"
	 + "				 \"lat\": -38.383494,\r\n"
	 + "				 \"lng\": 33.427362\r\n"
	 + "				 },\r\n"
	 + "				 \"accuracy\": 50,\r\n"
	 + "				 \"name\": \""+name+"\",\r\n"
	 + "				 \"phone_number\": \"(+91) 983 893 3937\",\r\n"
	 + "				 \"address\": \""+address+"\",\r\n"
	 + "				 \"types\": [\r\n"
	 + "				\"shoe park\",\r\n"
	 + "				 \"shop\"\r\n"
	 + "				 ],\r\n"
	 + "				 \"website\": \"http://google.com\",\r\n"
	 + "				 \"language\": \"French-IN\"\r\n"
	 + "				 }\r\n"
	 + "				 ";
	 
 }
}
