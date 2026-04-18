package com.banking.tests;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.banking.base.BaseClass;
import com.banking.pages.LoginPage;

// Notice how we use 'extends BaseClass' to inherit the setup/teardown logic
public class LoginTest extends BaseClass {

    @Test(enabled = false) // This test is disabled for now, but you can enable it to verify browser launch
    public void verifyBrowserLaunch() {
        // We get the driver from the ThreadLocal locker
        String currentTitle = getDriver().getTitle();
        System.out.println("The browser successfully launched and the title is: " + currentTitle);
    }
       @Test(dataProvider="loginCredentials", dataProviderClass=com.banking.utils.DataProviders.class)
       public void verifyMultipleLogin(String username, String password) {
           // 1. Create an instance of the LoginPage
           LoginPage loginPage = new LoginPage(getDriver());
		// 2. Use the login method to perform the login action
           System.out.println("Attempting to log in with user: " + username);
		loginPage.loginToParaBank(username,password);
		
		// --- THE ARCHITECT UPGRADE: Explicit Wait ---
        boolean isRedirected = false;
        
		try {
						// Wait for the title to contain "overview.htm" which indicates a successful login
			WebDriverWait wait=new WebDriverWait(getDriver(),Duration.ofSeconds(6));
			isRedirected = wait.until(ExpectedConditions.urlContains("overview.htm"));
		} catch (Exception e) {
			
			System.out.println("TIMEOUT: Waited 6 seconds, but URL never changed to overview.htm");//if 6 seconds pass and we are not redirected to the overview page, we will catch the exception and print a message
			isRedirected=false;
		}
		// --- ADD THIS DEBUG LINE ---
        System.out.println("DEBUG: The current browser URL is: " + getDriver().getCurrentUrl());
		   
	
		//judge now the result of the login attempt based on whether we were redirected to the overview page or not
		Assert.assertTrue(isRedirected, "Login failed for user: " + username);
		System.out.println("Login workflow executed for user: " + username);
		
	   }
	  
}