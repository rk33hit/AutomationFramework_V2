package com.banking.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
		 // This class will manage the ExtentReports instance and configuration
	 // You can add methods here to initialize the report, create test nodes, and flush the report at the end of the test suite	
	public static ExtentReports createInstance() {
		// 1. Create an instance of ExtentReports
		ExtentReports extent = new ExtentReports();
		
		// 2. Configure the report (e.g., set the report file path, add system info, etc.)
		String reportPath = System.getProperty("user.dir") + "/reports/ExtentReport.html";
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
		extent.attachReporter(sparkReporter);
		
		// Optional: Customize the report appearance
		sparkReporter.config().setDocumentTitle("ParaBank Test Automation report");
		sparkReporter.config().setTheme(Theme.DARK);
		sparkReporter.config().setReportName("Nightly execution results");
		
		
		// 3. Add any additional configuration or system info if needed
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("Tester", "Rohit");
		extent.setSystemInfo("Application", "ParaBank");
		
		return extent;
	}

}
