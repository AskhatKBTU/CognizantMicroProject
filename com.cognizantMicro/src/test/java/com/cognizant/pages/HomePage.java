package com.cognizant.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class HomePage extends SuperPageObject {
	
	
	public HomePage(WebDriver driverInstance, String url) {
		super(driverInstance, url);
		// TODO Auto-generated constructor stub
	}

	public HomePage openHomePage() {
		navigate("/");
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		return this;
	}
	

	public boolean validateLink(String locator) throws Exception {
		WebElement element = driver.findElement(By.xpath(locator));
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		safeJavaScriptClick(element);
		ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
		driver.switchTo().window(tabs2.get(1));
		verifyEquals(OR.getProperty("webinar_URL"),driver.getCurrentUrl());
		return true;
	}
	
	
	public boolean validateLinkWithoutJS (String locator) throws Exception {
		click(locator);
		verifyEquals(OR.getProperty("webinar_URL"),driver.getCurrentUrl());
		
		return true;
	} 

	

}
