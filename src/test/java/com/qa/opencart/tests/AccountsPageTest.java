package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic 104: Open Cart Application with Account Page Workflow")
@Story("User Story 105: Test Account page workflow for Open Cart Application")
public class AccountsPageTest extends BaseTest{
	
	@BeforeClass
	public void accSetUp() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Description("Checking account page title test")
	@Severity(SeverityLevel.MINOR)
	@Owner("Shaifu")
	@Feature("account page title feature")
	@Test
	public void accPageTitleTest() {
		Assert.assertEquals(accPage.getAccPageTitle(), AppConstants.ACCOUNT_PAGE_TITLE, AppError.TITLE_NOT_FOUND);
	}
	
	@Description("Checking account page url test")
	@Severity(SeverityLevel.MINOR)
	@Owner("Shaifu")
	@Feature("account page url feature")
	@Test
	public void accPageURLTest() {
		Assert.assertTrue(accPage.getAccPageURL().contains(AppConstants.ACCOUNT_PAGE_FRACTION_URL), AppError.URL_NOT_FOUND);
	}
	
	@Description("Checking account page header test")
	@Severity(SeverityLevel.NORMAL)
	@Owner("Shaifu")
	@Feature("account page side header feature")
	@Test
	public void accPageHeadersTest() {
		List<String> accPageHeadersList = accPage.getAccPageHeaders();
		Assert.assertEquals(accPageHeadersList, AppConstants.ACC_PAGE_HEADER_LIST, AppError.LIST_IS_NOT_MATCHED);
	}
	
	@DataProvider
	public Object[][] getSearchData() {
		return new Object [][] {
			{"macbook",3},
			{"imac",1},
			{"samsung",2},
			{"aqua",0}
		};
	}
	
	@Description("Verifying account page product search count test")
	@Severity(SeverityLevel.NORMAL)
	@Owner("Prakhar")
	@Feature("account page search feature")
	@Test(dataProvider = "getSearchData")
	public void searchTest(String searchKey, int resultCounts) {
		searchResultsPage = accPage.doSearch(searchKey);
		Assert.assertEquals(searchResultsPage.getSearchResultCount(), resultCounts, AppError.RESULT_COUNT_MISMATCHED);
	}
	
}














