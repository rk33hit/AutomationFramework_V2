package com.banking.api.tests;

import com.banking.api.base.ApiBaseClass;
import com.banking.api.base.AuthUtil;
import com.banking.api.pojo.Booking;
import com.banking.api.pojo.Bookingdates;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class DeleteBookingTest  extends ApiBaseClass {
	// This test DEPENDS on verifyCreateBooking
	// It runs ONLY if verifyCreateBooking passes
	// It uses the createdBookingId that Test 1 stored
	
	@Test
	public void verifyDeleteBooking() {
		// STEP 1 — Create a booking to delete
		Bookingdates dates=new Bookingdates("2026-05-01", "2026-05-10");
		Booking booking=new Booking("ToDelete","User",100,false,dates,"Breakfast");
		Response createResponse=
				given()
				.spec(requestSpec)
				.body(booking)
				.when()
				.post("/booking")
				.then()
				.statusCode(200)
				.extract().response();
		int bookingId=createResponse.jsonPath().getInt("bookingid");
		System.out.println("Created booking to delete: " + bookingId);
		
		// STEP 2 — Send DELETE request to delete the booking
		given()
		.spec(requestSpec)
		.cookie("token",AuthUtil.getAuthToken()) // add auth token as cookie
		.when()
		.delete("/booking/"+bookingId) // DELETE to /booking/{id} to delete
		.then()
		.statusCode(201); // restful-booker returns 201 for successful deletion
		
		System.out.println("Successfully deleted booking: " + bookingId);
		
		// STEP 3 — Verify it is actually gone
		given()
		.spec(requestSpec)
		.when()
		.get("/booking/"+bookingId) // GET the deleted booking
		.then()
		.statusCode(404); // should return 404 Not Found since it's deleted
	}
	
	@Test
	public void verifyDeleteWithoutAuth() {
		// Attempt to delete a booking without auth token
		given()
		.spec(requestSpec)
		.when()
		.delete("/booking/1") // trying to delete booking with ID 1
		.then()
		.statusCode(anyOf(equalTo(403),(equalTo(401)))); // should return 403 Forbidden since no auth provided
		
		System.out.println("Verified that deleting without auth is forbidden");
	}
		
	}

