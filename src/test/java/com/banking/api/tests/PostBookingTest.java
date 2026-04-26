package com.banking.api.tests;
import org.testng.annotations.Test;
import org.testng.Assert;

import com.banking.api.base.ApiBaseClass;
import com.banking.api.pojo.Booking;
import com.banking.api.pojo.Bookingdates;
import static io.restassured.RestAssured.*;
//import static io.restassured.response.ResponseOptions.*;
import static org.hamcrest.Matchers.*;
import io.restassured.response.Response;


public class PostBookingTest extends ApiBaseClass {
	// static variable to share booking ID between tests
    // static means it belongs to the CLASS not an instance
    // so Test 2 can read the ID that Test 1 created
	private static int createdBookingId;
	
	@Test
	public void verifyCreateBooking() {
		// STEP 1 — Build the nested dates object first
		Bookingdates dates=new Bookingdates("2026-05-01", "2026-05-10");
		 // STEP 2 — Build the main booking object
		Booking booking=new Booking("Rohit","Kumar",1500,true,dates,"Breakfast");
		
		// STEP 3 — Send the POST request with the booking object as the body
		Response response=
				given()
				.spec(requestSpec)
				.body(booking) // Jackson converts this Java object to JSON
				.when()
				.post("/booking")
				 // ↑ HTTP POST to https://restful-booker.herokuapp.com/booking
                // POST = create a new resource
				.then()
				.statusCode(200)
				// ↑ restful-booker returns 200 for successful creation
                // most APIs return 201 Created — but this one uses 200
				.body("bookingid",greaterThan(0)) // verify we got an ID back
				.body("booking.firstname",equalTo("Rohit"))
				.body("booking.lastname",equalTo("Kumar"))
				.body("booking.totalprice",equalTo(1500))
				.body("booking.depositpaid",equalTo(true))
				.body("booking.bookingdates.checkin",equalTo("2026-05-01"))
				.body("booking.bookingdates.checkout",equalTo("2026-05-10"))
				.body("booking.additionalneeds",equalTo("Breakfast"))
				.extract().response(); // extract the response for further assertions
		 // STEP 4 — Extract the booking ID from the response for use in the next test
		createdBookingId=response.jsonPath().getInt("bookingid");
	//  ↑ static variable    ↑ navigate to bookingid in response JSON
        // store it so Test 2 can use it

		System.out.println("Created booking id:"+createdBookingId);
		// STEP 5 — Assert the ID is a valid positive number
		Assert.assertTrue(createdBookingId>0, "Booking Id should be a positive number"+createdBookingId);
	}
		// This test DEPENDS on verifyCreateBooking
	    // It runs ONLY if verifyCreateBooking passes
	    // It uses the createdBookingId that Test 1 stored
		@Test(dependsOnMethods="verifyCreateBooking")
		public void verifyCreatedBookingExists() {
			// Verify the booking we created in Test 1 actually exists
			given()
			.spec(requestSpec)
			.when()
			.get("/booking/"+createdBookingId)
			// ↑ use the ID from Test 1
            // if createdBookingId = 47, this is GET /booking/47
			.then()
			.statusCode(200)
			.body("firstname",equalTo("Rohit"))
			.body("lastname", equalTo("Kumar"))
            .body("totalprice", equalTo(1500));
			
			System.out.println("Verifiied booking with id:"+createdBookingId+" exists");
			
		}
			
			@Test
			public void verifyCreateBookingWithMissingFields() {
				// Send only firstname — missing all other required fields
				String incompleteBody ="{\"firstname\":\"Rohit\"}";
				given()
	            .spec(requestSpec)
	            .body(incompleteBody)
				.when()	
				.post("/booking")
				.then()
				.statusCode(anyOf(is(400),(is(500)))); // API may return 400 Bad Request or 500 Internal Server Error
				 // anyOf means EITHER 400 OR 500 is acceptable
	            // we do not know exactly which one this API returns for bad input
		}
		
					
				
		
	}
	


