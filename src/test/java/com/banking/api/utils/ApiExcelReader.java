package com.banking.api.utils;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
public class ApiExcelReader {
	
	public static Object[][] getBookingData(String sheetName){
		Object[][] data =null;
		try {
			// Read Excel file from resources folder
			FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/BookingData.xlsx");
		//create workbook and sheet objects
			
			Workbook workbook=new XSSFWorkbook(fis);
			Sheet sheet=workbook.getSheet("Sheet1");
			DataFormatter formatter = new DataFormatter();
			//Get number of rows and columns
			int rowCount=sheet.getPhysicalNumberOfRows();
			int colCount=sheet.getRow(0).getLastCellNum();
			// Subtract 1 to skip header row
            data = new Object[rowCount - 1][colCount];
            			// Loop through rows and columns to populate data array
            for (int i = 1; i < rowCount; i++) {
                Row row = sheet.getRow(i);
                for (int j = 0; j < colCount; j++) {
                    data[i - 1][j] = formatter.formatCellValue(row.getCell(j));
                }
            }
            	workbook.close();
            	fis.close();
            	
            	
            
            } catch (IOException e) {
            				e.printStackTrace();
            				System.out.println("Error reading Excel file: " + e.getMessage());
            }
		return data;
		
	}

}
