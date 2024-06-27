package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;
import com.qa.opencart.exceptions.BrowserException;
import com.qa.opencart.exceptions.FrameworkException;

public class DriverFactory {
	WebDriver driver;
	Properties prop;
	
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	/**
	 * This used to initialize the driver on the basis of given browser name.
	 * 
	 * @param browserName
	 */
	public WebDriver initDriver(Properties prop) {

		String browserName = prop.getProperty("browser");

		System.out.println("browser name is : " + browserName);
		switch (browserName.toLowerCase().trim()) {
		case "chrome":
			tlDriver.set(new ChromeDriver());
			break;
		case "firefox":
			tlDriver.set(new FirefoxDriver());
			break;
		case "edge":
			tlDriver.set(new EdgeDriver());
			break;
		default:
			System.out.println("Please pass the right browser name... " + browserName);
			throw new BrowserException(AppError.BROWSER_NOT_FOUND);
		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));

		return getDriver();
	}
	
	/**
	 * get the local thread copy of the driver
	 * @return
	 */
	public static WebDriver getDriver() {
		return tlDriver.get();
	}
	
	/**
	 * This method is used to initialize the properties from the .properties file
	 * 
	 * @return this returns properties (prop)
	 */
	public Properties initProp() {

		prop = new Properties();
		FileInputStream ip = null;

		// mvn clean install -Denv="qa"
		String envName = System.getProperty("env");

		if (envName == null) {
			System.out.println("#### DEFAULT ENV Name #####");
			System.out.println("#                         #");
			System.out.println("#            QA           #");
			System.out.println("#                         #");
			System.out.println("###########################");
			try {
				ip = new FileInputStream(AppConstants.CONFIG_QA_FILE_PATH);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
//			System.out.println("############ ENV Name ############");
//			System.out.println("              " + envName);
//			System.out.println("##################################");
			try {
				switch (envName.trim().toLowerCase()) {
				case "qa":
					System.out.println("#            QA           #");
					ip = new FileInputStream(AppConstants.CONFIG_QA_FILE_PATH);
					break;
				case "stage":
					System.out.println("####     ENV Name      ####");
					System.out.println("#          STAGE          #");
					System.out.println("###########################");
					ip = new FileInputStream(AppConstants.CONFIG_STAGE_FILE_PATH);
					break;
				case "dev":
					System.out.println("####     ENV Name      ####");
					System.out.println("#           DEV           #");
					System.out.println("###########################");
					ip = new FileInputStream(AppConstants.CONFIG_DEV_FILE_PATH);
					break;
				case "uat":
					System.out.println("####     ENV Name      ####");
					System.out.println("#           UAT           #");
					System.out.println("###########################");
					ip = new FileInputStream(AppConstants.CONFIG_UAT_FILE_PATH);
					break;
				case "prod":
					System.out.println("####     ENV Name      ####");
					System.out.println("#          PROD           #");
					System.out.println("###########################");
					ip = new FileInputStream(AppConstants.CONFIG_FILE_PATH);
					break;

				default:
					System.out.println("############ ENV ###########");
					System.out.println("#        INVALID ENV       #");
					System.out.println("############################");
					throw new FrameworkException("=== WRONG ENV PASSED ===");
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;

	}
	
	/**
	 * take screenshot
	 */
	public static String getScreenshot(String methodName) {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);//temp location
		String path = System.getProperty("user.dir")+"/screenshots/"+methodName+"_"+System.currentTimeMillis()+".png";
		File destination = new File(path);
		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
	
}

















