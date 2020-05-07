package com.cognizant.pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.cognizant.framework.TestSuperClass;



public abstract class SuperPageObject extends TestSuperClass {

	
	protected String baseUrl;
	
	protected SuperPageObject(WebDriver driverInstance, String url) {
		
		driver = driverInstance;
		PageFactory.initElements(driver, this);
		baseUrl = url;
	}
	
	protected void navigate(String url) {
			
		driver.get(baseUrl+url);
		
	}
	
	


}
