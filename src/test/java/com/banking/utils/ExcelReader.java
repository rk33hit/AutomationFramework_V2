package com.banking.utils;

import java.io.FileInputStream;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

    public static Object[][] getExcelData(String sheetName) {
        Object[][] data = null;
        try {
            // 1. Dynamically locate the Excel file in your project
            String projectPath = System.getProperty("user.dir");
            FileInputStream file = new FileInputStream(projectPath + "/testData/LoginData.xlsx");

            // 2. Open the Workbook
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            
            // --- ARCHITECT DEBUG BLOCK START ---
            System.out.println("\n=== EXCEL DEBUG INFO ===");
            System.out.println("Name requested by DataProvider: [" + sheetName + "]");
            int numberOfSheets = workbook.getNumberOfSheets();
            System.out.println("Total sheets found in file: " + numberOfSheets);
            for (int k = 0; k < numberOfSheets; k++) {
                System.out.println("Sheet " + k + " actual name inside Excel: [" + workbook.getSheetName(k) + "]");
            }
            System.out.println("========================\n");
            // --- ARCHITECT DEBUG BLOCK END ---

            // 3. Select the specific Sheet
            XSSFSheet sheet = workbook.getSheet(sheetName);

            // 4. Count Rows and Columns
            int rowCount = sheet.getPhysicalNumberOfRows();
            int colCount = sheet.getRow(0).getLastCellNum();

            // 5. Create the 2D Array (subtract 1 from rowCount to skip the header row)
            data = new Object[rowCount - 1][colCount]; 

            // 6. Loop through Excel and populate the Array
            for (int i = 1; i < rowCount; i++) { // Start at i=1 to skip headers
                for (int j = 0; j < colCount; j++) {
                    // Extract text from cell and put it in the array
                    data[i - 1][j] = sheet.getRow(i).getCell(j).getStringCellValue();
                }
            }
            workbook.close();
            
        } catch (Exception e) {
            System.out.println("❌ CRITICAL ERROR: Could not read Excel File!");
            e.printStackTrace();
        }
        return data;
    }
}