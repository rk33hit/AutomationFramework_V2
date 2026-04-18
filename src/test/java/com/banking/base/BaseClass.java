package com.banking.base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseClass {
    
    // The Architect's Locker: Thread-safe WebDriver for Parallel Execution
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @BeforeMethod
    public void setUp() {
        // 1. Initialize Chrome and IMMEDIATELY lock it into the ThreadLocal locker
        driver.set(new ChromeDriver());
        
        // 2. Always use getDriver() from this point forward to interact with that specific thread's browser
        getDriver().manage().window().maximize();
        
        //3. Set an implicit wait (optional, but common in base classes)
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        // 3. Hitting the target banking application
        getDriver().get("https://parabank.parasoft.com/");
    }

    // Public getter so your Page Objects and Tests can access this thread's browser
    public static WebDriver getDriver() {
        return driver.get();
    }

    @AfterMethod
    public void tearDown() {
        // Safely quit the browser and clear the memory to prevent Jenkins server crashes
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove();
        }
    }
}