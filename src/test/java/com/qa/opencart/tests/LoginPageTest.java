package com.qa.opencart.tests;

import org.testng.Assert;
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

@Epic("Epic 100: Design Open Cart Application with Login Workflow")
@Story("User Story 101: Design login page for Open Cart Application")
public class LoginPageTest extends BaseTest {
	
	@Description("Checking login page title test")
	@Severity(SeverityLevel.MINOR)
	@Owner("Prakhar")
	@Feature("login page title test feature")
	@Test (priority = 1)
	public void loginPageTitleTest() {
		String actTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE, AppError.TITLE_NOT_FOUND);
	}
	
	@Description("Checking login page url test")
	@Severity(SeverityLevel.NORMAL)
	@Owner("Prakhar")
	@Feature("login pageurl feature")
	@Test (priority = 2)
	public void loginPageURLTest() {
		String actURL = loginPage.getLoginPageURL();
		Assert.assertTrue(actURL.contains(AppConstants.LOGIN_PAGE_FRACTION_URL), AppError.URL_NOT_FOUND);
	}
	
	@Description("Veryfing forgot password link")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Reena")
	@Feature("login page forgot password feature")
	@Test (priority = 3)
	public void forgotPwdExistTest() {
		Assert.assertTrue(loginPage.checkForgotPwdLinkExist(), AppError.ELEMENT_NOT_FOUND);
	}
	
	@Description("Checking user is logging into the account")
	@Severity(SeverityLevel.BLOCKER)
	@Owner("Prakhar")
	@Feature("login into the application feature")
	@Test (priority = 4)
	public void loginTest()  {
		accPage = loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
		Assert.assertEquals(accPage.getAccPageTitle(), AppConstants.ACCOUNT_PAGE_TITLE, AppError.TITLE_NOT_FOUND);
	}

}
