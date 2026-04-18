package com.banking.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.banking.base.BaseClass;

public class TestListener implements ITestListener {
	// This class will implement the ITestListener interface to listen to test events (like test start, success, failure, etc.)
	// You can add methods here to log test results, capture screenshots on failure, and integrate with ExtentReports
	
	//intilaize the report engine 
	private static ExtentReports extent =ExtentManager.createInstance();
	
	// ThreadLocal ensures ExtentReports  doesnot mipx  up logs when tests are run in parallel
	private static ThreadLocal<ExtentTest> testNode=new ThreadLocal<>();
	
	@Override
    public void onTestStart(ITestResult result) {
        // 1. Grab the default method name (e.g., "verifyMultipleLogin")
        String testName = result.getMethod().getMethodName();
        
        // 2. THE UPGRADE: Check if this test was fed by a DataProvider
        Object[] parameters = result.getParameters();
        
        // If parameters exist, and we have at least one (the username)
        if (parameters != null && parameters.length > 0) {
            // Append the username to the test name so it shows up in the Extent Report UI
            // parameters[0] is the username, parameters[1] is the password
            testName = testName + " - User: [" + parameters[0].toString() + "]";
        }

        // 3. Create the test node in Extent Reports with the custom name
        ExtentTest test = extent.createTest(testName);
        testNode.set(test);
    }
	
	@Override
	public void onTestSuccess(ITestResult result) {
		testNode.get().pass("Test passed");
	}
	
	@Override
	public void onTestFailure(ITestResult result) {
		//log exact error stack trace in the report
		testNode.get().fail(result.getThrowable());
		
		// Capture username from test method parameters
        Object[] parameters = result.getParameters();
        if (parameters != null && parameters.length > 0) {
            for (Object param : parameters) {
                if (param instanceof String && param.toString().contains("username")) {
                    testNode.get().info("Failed Username: " + param);
                    break;
                }
            }
        }
		
		//grab the specific threads browser and capture the screenshot and attach to the report
		WebDriver driver=BaseClass.getDriver();
		if(driver!=null) {
			String base64ScreenShot=((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
			testNode.get().addScreenCaptureFromBase64String (base64ScreenShot,"Failure Screenshot");
		}
		
	}
	
@Override
public void onTestSkipped(ITestResult result) {
	testNode.get().skip(result.getThrowable());

}

@Override
public void onFinish(ITestContext context) {
    extent.flush();
}
}
