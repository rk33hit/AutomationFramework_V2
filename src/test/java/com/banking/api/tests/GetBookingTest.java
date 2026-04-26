package com.banking.api.tests;

import org.testng.annotations.Test;
import org.testng.annotations.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*; 
import static io.restassured.response.ResponseOptions.*;
import org.testng.Assert;

import com.banking.api.base.ApiBaseClass;

import io.restassured.response.Response;

public class GetBookingTest extends ApiBaseClass  {
	@Test
	public void verifyGetAllBookings() {
		given()
		.spec(requestSpec)
		.when()
		.get("/booking")
		.then()
		.statusCode(200)
		.body("size()",greaterThan(0));
	}
	@Test
	public void verifyGetBookingById() {
		Response  allBookings = 
				given()
					.spec(requestSpec)
				.when()
					.get("/booking")
				
				.then()
					.statusCode(200)
					.extract().response();
		 // STEP 2 — extract the first booking ID from the response
		int firstBookingId=allBookings.jsonPath().getInt("[0].bookingid");
		System.out.println("First booking ID: " + firstBookingId);
		// STEP 3 — now GET that specific booking
		given()
			.spec(requestSpec)
			.when()
			.get("/booking/" + firstBookingId)
			.then()
			.statusCode(200)
			.body("firstname",notNullValue())
			.body("lastname",notNullValue())
			.body("totalprice",greaterThan(0))
			
			
				
	}
	
	@Test
	public  void verifySingleBookingResponseStructure() {
		Response allBookings=
				given()
					.spec(requestSpec)
				.when()
					.get("/booking")
				.then()
					.statusCode(200)
					.extract().response();
		
		int firstBookingId=allBookings.jsonPath().getInt("[0].bookingid");
		 // GET the booking and store full response
		 // GET the booking and store full response
        Response response =
            given()
                .spec(requestSpec)
            .when()
                .get("/booking/" + firstBookingId)
            .then()
                .statusCode(200)
                .extract().response();
        			// ↑ store full response — we need to extract multiple fields
        // Extract individual fields from the response
        String FirstName=response.jsonPath().getString("firstname");
        String LastName=response.jsonPath().getString("lastname");
        int TotalPrice=response.jsonPath().getInt("totalprice");
        boolean DepositPaid=response.jsonPath().getBoolean("depositpaid");
        // Print them — useful during development to see actual values
        System.out.println("First Name: " + FirstName);
        System.out.println("Last Name: " + LastName);
        System.out.println("Total Price: " + TotalPrice);
        System.out.println("Deposit Paid: " + DepositPaid);
         // Assertions — verify the fields have expected values
        Assert.assertNotNull(FirstName,"First name should not be null");
        Assert.assertNotNull(LastName,"Last name should not be null");
        Assert.assertTrue(TotalPrice>0,"Total price should be greater than 0");
        Assert.assertTrue(DepositPaid==true||DepositPaid==false,"Deposit paid should be true or false");
        

		
	}
	
	

}
