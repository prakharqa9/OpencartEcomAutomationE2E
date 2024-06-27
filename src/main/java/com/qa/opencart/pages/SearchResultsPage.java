package com.qa.opencart.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

import io.qameta.allure.Step;

public class SearchResultsPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By searchResult = By.cssSelector("div.product-thumb");
	
	public SearchResultsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	@Step("getting search result count of the product")
	public int getSearchResultCount() {
		List<WebElement> resultList = 
				eleUtil.waitForVisibilityOfElemenetsLocated(searchResult, TimeUtil.DEFAULT_MEDIUM_TIME);
		int resultCount = resultList.size();
		System.out.println("product search results count === "+resultCount);
		return resultList.size();
	}
	
	@Step("getting prdoct information for : {0}")
	public ProductInfoPage selectProduct(String productName) {
		eleUtil.doClick(By.linkText(productName), TimeUtil.DEFAULT_TIME);
		return new ProductInfoPage(driver); 
	}
}
