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
    public void verifyMultipleLogin(String username, String password, String expectedResult) {
        
        // 1. Create an instance of the LoginPage
        LoginPage loginPage = new LoginPage(getDriver());
        
        // 2. Skip test if data is null or empty
        if (username == null || username.isEmpty() || username.equals("null")) {
            System.out.println("Skipping empty row");
            return;
        }
        
        // 3. Perform login
        System.out.println("Attempting to log in with user: " + username);
        loginPage.loginToParaBank(username, password);

        // 4. Wait for redirect
        boolean isRedirected = false;
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(6));
            isRedirected = wait.until(ExpectedConditions.urlContains("overview.htm"));
        } catch (Exception e) {
            System.out.println("TIMEOUT: Waited 6 seconds, URL never changed");
            isRedirected = false;
        }

        System.out.println("DEBUG: Current URL is: " + getDriver().getCurrentUrl());

        // 5. Assert based on expected column from Excel
        boolean shouldPass = expectedResult.equalsIgnoreCase("true");
        if (shouldPass) {
            Assert.assertTrue(isRedirected, "VALID user login failed: " + username);
            System.out.println("PASS: Login successful for valid user: " + username);
        } else {
            Assert.assertFalse(isRedirected, "INVALID user login should have failed: " + username);
            System.out.println("PASS: Login correctly rejected for invalid user: " + username);
        }
    }
	  
}