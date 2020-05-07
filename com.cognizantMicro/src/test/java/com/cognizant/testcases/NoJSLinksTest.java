package com.cognizant.testcases;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.cognizant.framework.TestSuperClass;
import com.cognizant.pages.HomePage;
import com.cognizant.utilities.TestUtil;

public class NoJSLinksTest extends TestSuperClass {
	
	@Test
	public void validateLinkNoJS() throws Exception {
	
		if(!(TestUtil.isTestRunnable("NoJSLinksTest", excel))){
			
			throw new SkipException("Skipping the test "+"openAccountTest".toUpperCase()+ "as the Run mode is NO");
		}	
		
	boolean actualResult = new HomePage(driver, url) 
			.openHomePage()
			.validateLinkWithoutJS(OR.getProperty("webinar_XPATH"));
		
	
		Assert.assertTrue(actualResult, "Something went wrong on validateLinkNoJS test !!!");
		System.out.println("Validate Link Without JS test has been executed successfully !!!");
		
     }	
	
	
	
}
