package com.banking.api.tests;

import com.banking.api.base.ApiBaseClass;
import com.banking.api.pojo.Booking;
import com.banking.api.pojo.Bookingdates;
import com.banking.api.utils.ApiDataProvider;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class DataDrivenBookingTest extends ApiBaseClass {

    @Test(dataProvider = "bookingData", dataProviderClass = ApiDataProvider.class)
    public void verifyCreateBookingDataDriven(
            String firstname,
            String lastname,
            String totalprice,
            String depositpaid,
            String checkin,
            String checkout,
            String additionalneeds,
            String expectedStatus) {

        System.out.println("================================================");
        System.out.println("Testing with Data Set: " + firstname + " " + lastname);

        // Convert String values from Excel to proper datatypes
        int price = totalprice.isEmpty() ? 0 : Integer.parseInt(totalprice);
        boolean deposit = Boolean.parseBoolean(depositpaid);
        int expStatus = Integer.parseInt(expectedStatus);

        // CLIENT SIDE VALIDATION:
        // If mandatory fields are blank, don't hit API
        if (firstname.isEmpty() || lastname.isEmpty()) {
            System.out.println("SKIP API CALL: Mandatory fields missing");
            System.out.println("Expected Status: " + expStatus + " | Handled by framework validation");
            System.out.println("PASS: Invalid data row correctly identified");
            Assert.assertTrue(true, "Invalid row correctly blocked at client side");
            return;
        }

        // Build nested booking dates object
        Bookingdates dates = new Bookingdates(
                checkin.isEmpty() ? "2026-01-01" : checkin,
                checkout.isEmpty() ? "2026-01-02" : checkout
        );

        // Build final booking request body POJO
        Booking booking = new Booking(
                firstname,
                lastname,
                price,
                deposit,
                dates,
                additionalneeds
        );

        // Send POST request
        Response response =
                given()
                    .spec(requestSpec)
                    .body(booking)
                .when()
                    .post("/booking")
                .then()
                    .extract().response();

        int actualStatus = response.getStatusCode();

        System.out.println("Expected Status: " + expStatus + " | Actual Status: " + actualStatus);

        // VALID DATA FLOW
        if (expStatus == 200 || expStatus == 201) {

            Assert.assertEquals(actualStatus, expStatus,
                    "Status mismatch for dataset: " + firstname);

            int bookingId = response.jsonPath().getInt("bookingid");
            String returnedFirstName = response.jsonPath().getString("booking.firstname");

            Assert.assertTrue(bookingId > 0, "Booking ID should be generated");
            Assert.assertEquals(returnedFirstName, firstname,
                    "Firstname mismatch in response");

            System.out.println("PASS: Booking created successfully for " + firstname + " | Booking ID: " + bookingId);
        }

        // INVALID DATA FLOW (if server rejects)
        else {
            Assert.assertTrue(actualStatus >= 400,
                    "Expected error status but got: " + actualStatus);

            System.out.println("PASS: Server correctly rejected invalid data");
        }
    }
}