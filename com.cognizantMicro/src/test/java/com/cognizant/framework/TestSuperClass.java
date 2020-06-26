package com.cognizant.framework;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cognizant.utilities.ExcelReader;
import com.cognizant.utilities.TestUtil;
import com.cognizant.utilities.ExtentManager;
import com.cognizant.framework.ConfigurationManager;
import com.cognizant.framework.DriverManager;
import com.cognizant.framework.DriverManagerFactory;
import com.cognizant.framework.Settings;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestSuperClass {

	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static ExcelReader excel = new ExcelReader(System.getProperty("user.dir")+"/src/test/resources/com/cognizant/excel/testdata.xlsx");
	public static WebDriverWait wait;
	public static ExtentTest test;
	public ExtentReports rep = ExtentManager.getInstance();
	protected String url;
	protected DriverManager driverManager;
	private ConfigurationManager configurationManager;

	protected TestSuperClass() {
		this.configurationManager = ConfigurationManager
				.getInstance()
				.getConfiguration();
	}
	
	protected TestSuperClass (String url) {
		this();
		this.configurationManager.SetIfValueNotNull(Settings.baseUrl, url);
	}

	@Parameters({
		Settings.baseUrl,
		Settings.browserType, 
		Settings.databaseConnectionString, 
		Settings.implicitWaitMilliseconds, 
		Settings.pageLoadTimeoutMilliseconds
	})
	@BeforeTest
	public void readSettings(
			@Optional String baseUrl,
			@Optional String browserType, 
			@Optional String databaseConnectionString, 
			@Optional String implicitWaitMilliseconds, 
			@Optional String pageLoadTimeoutMilliseconds) {
		
		configurationManager.SetIfValueNotNull(Settings.baseUrl, baseUrl)
							.SetIfValueNotNull(Settings.browserType, browserType)
							.SetIfValueNotNull(Settings.databaseConnectionString, databaseConnectionString)
							.SetIfValueNotNull(Settings.implicitWaitMilliseconds, implicitWaitMilliseconds)
							.SetIfValueNotNull(Settings.pageLoadTimeoutMilliseconds, pageLoadTimeoutMilliseconds);	
	}

	@BeforeMethod
	public void setup() throws IOException {
		if (driver==null) {
		fis = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/com/cognizant/properties/Config.properties");
		config.load(fis);
	
		fis = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/com/cognizant/properties/OR.properties");
		OR.load(fis);
		}
		this.driverManager = DriverManagerFactory.getManager(configurationManager.getValueAsString(Settings.browserType));
		this.driver = driverManager.getDriver();	
		
		this.driver.manage().timeouts().implicitlyWait(configurationManager.getValueAsLong(Settings.implicitWaitMilliseconds), Settings.timeoutTimeUnit);
		this.driver.manage().timeouts().pageLoadTimeout(configurationManager.getValueAsLong(Settings.pageLoadTimeoutMilliseconds), Settings.timeoutTimeUnit);
		this.url = configurationManager.getValueAsString(Settings.baseUrl);
		
	}

	@AfterMethod
	public void tearDown() {
		driverManager.quitDriver();
	}

	public void click(String locator) {

		if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		} else if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).click();
		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).click();
		}
		test.log(LogStatus.INFO, "Clicking on : " + locator);
	}

	public void type(String locator, String value) {

		if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
		}

		test.log(LogStatus.INFO, "Typing in : " + locator + " entered value as " + value);

	}


	public boolean IsElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		}
		catch(NoSuchElementException e){
			return false;
		}
		
	}

	static WebElement dropdown;

	public void select(String locator, String value) {

		if (locator.endsWith("_CSS")) {
			dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
		} else if (locator.endsWith("_XPATH")) {
			dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
		} else if (locator.endsWith("_ID")) {
			dropdown = driver.findElement(By.id(OR.getProperty(locator)));
		}
		
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);

		test.log(LogStatus.INFO, "Selecting from dropdown : " + locator + " value as " + value);

	}



	public static void verifyEquals(String expected, String actual) throws IOException {

		try {

			Assert.assertEquals(actual, expected);

		} catch (Throwable t) {

			TestUtil.captureScreenshot();
			// ReportNG
			Reporter.log("<br>" + "Verification failure : " + t.getMessage() + "<br>");
			Reporter.log("<a target=\"_blank\" href=" + TestUtil.screenshotName + "><img src=" + TestUtil.screenshotName
					+ " height=200 width=200></img></a>");
			Reporter.log("<br>");
			Reporter.log("<br>");
			// Extent Reports
			test.log(LogStatus.FAIL, " Verification failed with exception : " + t.getMessage());
			test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));

		}

	}
	
	public void safeJavaScriptClick(WebElement element) throws Exception {
		try {
			if (element.isEnabled() && element.isDisplayed()) {
				System.out.println("Clicking on element with using java script click");

				((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			} else {
				System.out.println("Unable to click on element");
			}
		} catch (StaleElementReferenceException e) {
			System.out.println("Element is not attached to the page document "+ e.getStackTrace());
		} catch (NoSuchElementException e) {
			System.out.println("Element was not found in DOM "+ e.getStackTrace());
		} catch (Exception e) {
			System.out.println("Unable to click on element "+ e.getStackTrace());
		}
	}


	
	
}
