package com.banking.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
	
	
	
	private WebDriver driver;
	//1.locators (strictly private) so they can't be accessed outside this class
	private By usernameField=By.name("username");
	private By passwordField=By.name("password");
	private By loginButton=By.xpath("//input[@value='Log In']");
	
	//2. Constructor to initialize the WebDriver (and any other setup if needed)
	public LoginPage(WebDriver driver) {
		this.driver=driver;
	}
	//3. page actions (Public: Tests can call these to interact with the page)
	public void enterUsername(String username) {
		driver.findElement(usernameField).sendKeys(username);
	}
	public void enterPassword(String password) {
		driver.findElement(passwordField).sendKeys(password);
	}
	public void clickLogin() {
		driver.findElement(loginButton).click();
	}
	//architect level: a single wrapper method for entire workflow of logging in, so tests can call just this one method if they want
	public void loginToParaBank(String username,String password) {
		enterUsername(username);
		enterPassword(password);
		clickLogin();
	}

	

}
