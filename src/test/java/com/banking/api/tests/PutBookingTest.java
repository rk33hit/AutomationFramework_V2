package com.banking.api.tests;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import com.banking.api.base.ApiBaseClass;
import com.banking.api.base.AuthUtil;
import com.banking.api.pojo.Booking;
import com.banking.api.pojo.Bookingdates;

import io.restassured.response.Response;

public class PutBookingTest extends ApiBaseClass {
	@Test
	public void verifyUpdateBooking() {
		 // STEP 1 — Create a booking first so we have an ID to update
		Bookingdates dates = new Bookingdates("2026-05-01", "2026-05-10");
        Booking originalBooking = new Booking(
            "Rohit", "Kumar", 1500, true, dates, "Breakfast"
        );
        Response createResponse=
        		given()
        		.spec(requestSpec)
        		.body(originalBooking)
        		.when()
        		.post("/booking")
        		.then()
        		.statusCode(200)
        		.extract().response();
        int bookingId=createResponse.jsonPath().getInt("bookingid");
        System.out.println("Created booking to update: " + bookingId);
        // STEP 2 — Build updated booking object
        // changing firstname, totalprice and dates
        Bookingdates newDates=new Bookingdates("2026-06-01", "2026-06-15");
        Booking updatedBooking =new Booking ("Rahul", "Kumar", 2000, true, newDates, "Lunch");
        // STEP 3 — Send PUT request to update the booking
        given()
        .spec(requestSpec)
        .cookie("token",AuthUtil.getAuthToken()) // add auth token as cookie
     // ↑ THIS IS THE AUTH PART
        // restful-booker uses cookie-based auth
        // we pass the token as a cookie named "token"
        // some APIs use header: .header("Authorization", "Bearer " + token)
        // this API uses cookie — both approaches common in real systems
        .body(updatedBooking)
        .when()
        .put("/booking/"+bookingId) // PUT to /booking/{id} to update
        // ↑ PUT to specific booking ID
        // PUT = full update — replaces entire resource
        .then()
        .statusCode(200)
        .body("firstname",equalTo("Rahul"))
        .body("lastname",equalTo("Kumar"))
        .body("totalprice",equalTo(2000))
        .body("depositpaid",equalTo(true))
        .body("bookingdates.checkin",equalTo("2026-06-01"))
        .body("bookingdates.checkout",equalTo("2026-06-15"))
        .body("additionalneeds",equalTo("Lunch"));
        System.out.println("Successfully updated booking: " + bookingId);
        
		
	}
	
	@Test
	public void verifyUpdateBookingWithoutAuth() {
		// NEGATIVE TEST — try to update without token
        // server should reject with 403 Forbidden
		Bookingdates newDates=new Bookingdates("2026-06-01", "2026-06-15");
				Booking updatedBookingg =new Booking ("TEST", "USER", 2000, false, newDates, "Lunch");
				given()
		.spec(requestSpec)
		// no auth token this time
		.body(updatedBookingg)
		.when()
		.put("/booking/1") // try to update booking ID 1
		.then()
		.statusCode(anyOf(equalTo(403),equalTo(401))); // should be forbidden without auth
				// ↑ 403 Forbidden or 401 Unauthorised
	            // server should reject unauthenticated update attempts
	}
	

}
