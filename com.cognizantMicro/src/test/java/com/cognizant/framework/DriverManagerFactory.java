package com.cognizant.framework;


import com.cognizant.framework.BrowserTypes;
import com.cognizant.framework.ChromeDriverManager;
import com.cognizant.framework.DriverManager;
import com.cognizant.framework.FirefoxDriverManager;
import com.cognizant.framework.OperaDriverManager;

public class DriverManagerFactory {

	public static DriverManager getManager(String browserType) {
		
		if(browserType.equals(BrowserTypes.Chrome)) {
			return new ChromeDriverManager();
		}
		
		if(browserType.equals(BrowserTypes.Firefox)) {
			return new FirefoxDriverManager();
		}
		
		if(browserType.equals(BrowserTypes.Opera)) {
			return new OperaDriverManager();
		}
		
		
			
		throw new RuntimeException(browserType + " is not a supported browser.");
	}
}