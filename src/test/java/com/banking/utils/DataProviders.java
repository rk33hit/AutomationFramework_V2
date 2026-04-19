package com.banking.utils;

import org.testng.annotations.DataProvider;

public class DataProviders {

    // THE MAGIC WORD: parallel = true
    @DataProvider(name = "loginCredentials", parallel = true)
    public static Object[][] getLoginData() {
        
        // We simply ask the ExcelReader for the data!
        // NOTE: Ensure "Sheet1" exactly matches the tab name at the bottom of your Excel file.
        return ExcelReader.getExcelData("Sheet1"); 
        
    }
}