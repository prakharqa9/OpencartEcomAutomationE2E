package com.qa.opencart.pages;

import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

public class ViewCartPage {
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	//1. Page Objects: By Locators:
	
	// 2. public constructor of the page
	public ViewCartPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	//3. public page actions/methods
	public String getViewCartPageTitle() {
		String title = eleUtil.waitForTitleToBe(AppConstants.CART_PAGE_TITLE, TimeUtil.DEFAULT_TIME);
		return title;
	}
}
