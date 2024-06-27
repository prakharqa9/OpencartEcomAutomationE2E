package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;
import com.qa.opencart.utils.ExcelUtil;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic 102: Open Cart Application with Selected Product Workflow")
@Story("User Story 103: Test product page information for Open Cart Application")
public class ProductInfoPageTest extends BaseTest{
	
	@BeforeClass
	public void productPageSetup() {
		accPage =  loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@DataProvider
	public Object[][] getProductData() {
		return new Object [][] {
			{"macbook","MacBook Pro"},
			{"imac","iMac"},
			{"samsung","Samsung SyncMaster 941BW"},
			{"samsung","Samsung Galaxy Tab 10.1"},
			{"canon","Canon EOS 5D"}
		};
	}
	
	@Description("Checking product header test")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Prakhar")
	@Feature("product title test feature")
	@Test(dataProvider = "getProductData")
	public void productHeaderTest(String searchKey, String productName) {
		searchResultsPage = accPage.doSearch(searchKey);
		productInfoPage = searchResultsPage.selectProduct(productName);
		Assert.assertEquals(productInfoPage.getProductHeader(), productName, AppError.HEADER_NOT_FOUND);
		
	}
	
	@DataProvider
	public Object[][] getProductImageData() {
		return new Object [][] {
			{"macbook","MacBook Pro",4},
			{"imac","iMac",3},
			{"samsung","Samsung SyncMaster 941BW",1},
			{"samsung","Samsung Galaxy Tab 10.1",7},
			{"canon","Canon EOS 5D",3}
		};
	}
	
	@DataProvider
	public Object[][] getProductImageDataFromSheet() {
		return ExcelUtil.getTestData(AppConstants.PRODUCT_IMAGE_SHEET_NAME);
	}
	
	@Description("Checking product page image count test")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Ashish")
	@Feature("product image test feature")
	@Test(dataProvider = "getProductImageDataFromSheet")
	public void productImagesTest(String searchKey, String productName, String imagesCount) {
		searchResultsPage = accPage.doSearch(searchKey);
		productInfoPage = searchResultsPage.selectProduct(productName);
		Assert.assertEquals(productInfoPage.getProductImagesCount(), Integer.parseInt(imagesCount), AppError.IMAGES_NOT_MISMATCHED);	
	}
	
	@Description("Verifying product information test")
	@Severity(SeverityLevel.BLOCKER)
	@Owner("Prakhar")
	@Feature("product information feature")
	@Test
	public void productInfoTest() {
		searchResultsPage = accPage.doSearch("macbook");
		productInfoPage = searchResultsPage.selectProduct("MacBook Pro");
		Map<String, String> productInfoMap =  productInfoPage.getProductInfoMap();
		System.out.println("********** Product Information Start **********");
		System.out.println(productInfoMap);
		System.out.println("********** Product Information End **********");
		softAssert.assertEquals(productInfoMap.get("productname"), "MacBook Pro");
		softAssert.assertEquals(productInfoMap.get("productimagescount"), "4");
		softAssert.assertEquals(productInfoMap.get("Brand"), "Apple");
		softAssert.assertEquals(productInfoMap.get("Product Code"), "Product 18");
		softAssert.assertEquals(productInfoMap.get("Reward Points"), "800");
		softAssert.assertEquals(productInfoMap.get("Availability"), "In Stock");
		softAssert.assertEquals(productInfoMap.get("productprice"), "$2,000.00");
		softAssert.assertEquals(productInfoMap.get("exTaxPrice"), "$2,000.00");
		
		softAssert.assertAll();//failure count
	}
	
}
