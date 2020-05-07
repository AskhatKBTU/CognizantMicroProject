package com.cognizant.framework;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ChromeDriverManager extends DriverManager {

	@Override
	protected void startService() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void stopService() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void createDriver() {
		// TODO Auto-generated method stub
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		long time = 5000;
		TimeUnit unit= TimeUnit.MILLISECONDS;
		driver.manage().timeouts().implicitlyWait(time , unit);
		//driver.manage().timeouts().pageLoadTimeout(time, unit);
	}
	

}
