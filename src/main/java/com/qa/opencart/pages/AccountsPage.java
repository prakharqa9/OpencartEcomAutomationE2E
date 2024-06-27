package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

import io.qameta.allure.Step;

public class AccountsPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	private By logoutLink = By.linkText("Logout");
	private By headers = By.cssSelector("div#content h2");
	private By search = By.name("search");
	private By searchIcon = By.cssSelector("div#search button");
	
	@Step("getting account page title")
	public String getAccPageTitle() {
		String title = eleUtil.waitForTitleToBe(AppConstants.ACCOUNT_PAGE_TITLE, TimeUtil.DEFAULT_TIME);
		System.out.println("Account page title : "+title);
		return title;
	}
	
	@Step("getting account page url")
	public String getAccPageURL() {
		String url = eleUtil.waitForURLContains(AppConstants.ACCOUNT_PAGE_FRACTION_URL, TimeUtil.DEFAULT_LONG_TIME);
		System.out.println("Account page url : "+url);
		return url;
	}
	
	@Step("getting the state of logout link exist")
	public boolean isLogoutLinkExist() {
		return eleUtil.doIsDisplayed(logoutLink);
	}
	
	@Step("getting account page side headers")
	public List<String> getAccPageHeaders() {
		List<WebElement> headersList =  eleUtil.waitForVisibilityOfElemenetsLocated(headers, TimeUtil.DEFAULT_MEDIUM_TIME);
		List<String> headerValList = new ArrayList<String>();
		for(WebElement e:headersList) {
			String text = e.getText();
			headerValList.add(text);
		}
		return headerValList;
	}
	
	@Step("getting the state of search field exist")
	public boolean isSearchExist() {
		return eleUtil.doIsDisplayed(search);
	}
	
	@Step("searching for product: {0}")
	public SearchResultsPage doSearch(String searchKey) {
		System.out.println("Searching for product : "+searchKey);
		if(isSearchExist()) {
			eleUtil.doSendKeys(search, searchKey);
			eleUtil.doClick(searchIcon);
			return new SearchResultsPage(driver);
		}else {
			System.out.println("Search field is not present on the page");
			return null;
		}
	}
	
	
}
