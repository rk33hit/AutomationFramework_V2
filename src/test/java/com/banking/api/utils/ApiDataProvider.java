package com.banking.api.utils;

import org.testng.annotations.DataProvider;
public class ApiDataProvider {
	@DataProvider(name="bookingData")
	public static Object[][] getBookingTestData() {
		return ApiExcelReader.getBookingData("CreateBooking");
		
	}
	

}
