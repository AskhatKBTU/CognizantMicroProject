package com.cognizant.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.cognizant.framework.TestSuperClass;
import com.cognizant.pages.HomePage;
import com.cognizant.utilities.TestUtil;

public class SimpleLinksTest extends TestSuperClass {


	@Test
	public void validateLink() throws Exception {
	
	if(!(TestUtil.isTestRunnable("SimpleLinksTest", excel))){
			throw new SkipException("Skipping the test "+"openAccountTest".toUpperCase()+ "as the Run mode is NO");
		}	
			
		
	boolean actualResult = new HomePage(driver, url) 
			.openHomePage()
			.validateLink(OR.getProperty("webinar_XPATH"));
		
	
	 Assert.assertTrue(actualResult, "Something went wrong on KeyPresses test !!!");
		System.out.println("Validate Link test has been executed successfully !!!");
		
     }	
	

}
	

