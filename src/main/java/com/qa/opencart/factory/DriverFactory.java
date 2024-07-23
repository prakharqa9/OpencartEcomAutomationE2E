package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
//import java.net.URL;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;
import com.qa.opencart.exceptions.BrowserException;
import com.qa.opencart.exceptions.FrameworkException;

public class DriverFactory {
	WebDriver driver;
	Properties prop;
	OptionsManager optionsManager;
	
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	/**
	 * This used to initialize the driver on the basis of given browser name. 
	 * @param browserName
	 */
	public WebDriver initDriver(Properties prop) {

		String browserName = prop.getProperty("browser");
		optionsManager = new OptionsManager(prop);

		System.out.println("browser name is : " + browserName);
		switch (browserName.toLowerCase().trim()) {
		case "chrome":
			if(Boolean.parseBoolean(prop.getProperty("remote"))) {
				//run tc on remote machine/grid
				initRemoteDriver("chrome");
			}else {
				//run in local
				tlDriver.set(new ChromeDriver());
			}
			break;
		case "firefox":
			if(Boolean.parseBoolean(prop.getProperty("remote"))) {
				//run tc on remote machine/grid
				initRemoteDriver("firefox");
			}else {
				//run in local
				tlDriver.set(new FirefoxDriver());
			}
			break;
		case "edge":
			if(Boolean.parseBoolean(prop.getProperty("remote"))) {
				//run tc on remote machine/grid
				initRemoteDriver("edge");
			}else {
				//run in local
				tlDriver.set(new EdgeDriver());
			}
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
	 * Initialize driver to run test cases on remote (grid) machine
	 * @param browserName
	 */
	private void initRemoteDriver(String browserName) {
		System.out.println("Running it on GRID... with browser: "+browserName);
		try {
		switch (browserName) {
		case "chrome":
				tlDriver.set(new RemoteWebDriver(new URI(prop.getProperty("huburl")).toURL(),optionsManager.getChromeOptions()));
			break;
		case "firefox":
			tlDriver.set(new RemoteWebDriver(new URI(prop.getProperty("huburl")).toURL(),optionsManager.getFirefoxOptions()));
		break;
		case "edge":
			tlDriver.set(new RemoteWebDriver(new URI(prop.getProperty("huburl")).toURL(),optionsManager.getEdgeOptions()));
		break;
		
		default:
			System.out.println("Please pass the right browser on grid");
			throw new BrowserException(AppError.BROWSER_NOT_FOUND);
		}
		}catch (MalformedURLException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

	/**
	 * get the local thread copy of the driver
	 * 
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
			System.out.println("#      Default ENV:  QA      #");
			try {
				ip = new FileInputStream(AppConstants.CONFIG_QA_FILE_PATH);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			try {
				switch (envName.trim().toLowerCase()) {
				case "qa":
					System.out.println("#        ENV Name:    QA           #");
					ip = new FileInputStream(AppConstants.CONFIG_QA_FILE_PATH);
					break;
				case "stage":
					System.out.println("#        ENV Name:  STAGE          #");
					ip = new FileInputStream(AppConstants.CONFIG_STAGE_FILE_PATH);
					break;
				case "dev":
					System.out.println("#         ENV Name:  DEV           #");
					ip = new FileInputStream(AppConstants.CONFIG_DEV_FILE_PATH);
					break;
				case "uat":
					System.out.println("#      ENV Name:      UAT           #");
					ip = new FileInputStream(AppConstants.CONFIG_UAT_FILE_PATH);
					break;
				case "prod":
					System.out.println("#         ENV Name:  PROD           #");
					ip = new FileInputStream(AppConstants.CONFIG_FILE_PATH);
					break;

				default:
					System.out.println("#        INVALID ENV       #");
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
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);// temp location
		String path = System.getProperty("user.dir") + "/screenshots/" + methodName + "_" + System.currentTimeMillis()
				+ ".png";
		File destination = new File(path);
		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}

}
