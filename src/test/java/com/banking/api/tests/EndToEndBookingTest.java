package com.banking.api.tests;
import com.banking.api.base.ApiBaseClass;
import com.banking.api.base.AuthUtil;
import com.banking.api.pojo.Booking;
import com.banking.api.pojo.Bookingdates;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class EndToEndBookingTest extends ApiBaseClass {
	@Test
	public void verifyCompleteBookingLifecycle() {
		System.out.println("=== STARTING END-TO-END BOOKING TEST ===");
		Bookingdates dates=new Bookingdates("2026-05-01", "2026-05-10");
		 Booking booking = new Booking(
		            "Rohit", "Kumar", 1500, true, dates, "Breakfast"
		        );
		 Response createResponse =
		            given().spec(requestSpec).body(booking)
		            .when().post("/booking")
		            .then().statusCode(200)
		            .body("booking.firstname", equalTo("Rohit"))
		            .extract().response();
		 int bookingId = createResponse.jsonPath().getInt("bookingid");
		 System.out.println("Created ID: " + bookingId);
		 System.out.println("=== STEP 2: VERIFY CREATED ===");
		 given().spec(requestSpec)
		 .when()
		 .get("/booking/"+bookingId)
		 .then().statusCode(200)
		 .body("firstname",equalTo("Rohit"))
		 .body("lastname",equalTo("Kumar"))
		 .body("totalprice",equalTo(1500))
		 .body("depositpaid",equalTo(true));
		 
		 System.out.println("=== STEP 3: UPDATE BOOKING ===");
		 Bookingdates newDates = new Bookingdates("2026-06-01", "2026-06-15");
	        Booking updatedBooking = new Booking(
	            "Rahul", "Kumar", 2000, true, newDates, "Lunch"
	        );
		 given().spec(requestSpec)
		 .cookie("token",AuthUtil.getAuthToken())
		 .body(updatedBooking)
		 .when()
		 .put("/booking/"+bookingId)
		 .then().statusCode(200)
		 .body("firstname",equalTo("Rahul"))
		 .body("totalprice",equalTo(2000));
		 
		 System.out.println("=== STEP 4: DELETE BOOKING ===");
		 given().spec(requestSpec)
		 .cookie("token",AuthUtil.getAuthToken())	
		 .when().delete("/booking/"+bookingId)
		 .then().statusCode(201);
		 
		 System.out.println("=== STEP 5: VERIFY DELETION ===");
		 given().spec(requestSpec)
		 .when().get("/booking/"+bookingId)
		 .then().statusCode(404);
		 
		 System.out.println("=== END OF END-TO-END BOOKING TEST ===");
		 System.out.println("Booking \" + bookingId + \":, successfully created, updated, deleted, and verified.");
		 
		 

	}
	}


