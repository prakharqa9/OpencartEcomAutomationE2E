package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;
import com.qa.opencart.listeners.ExtentReportListener;
import com.qa.opencart.listeners.TestAllureListener;
import com.qa.opencart.utils.ExcelUtil;
import com.qa.opencart.utils.StringUtils;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic 106: Open Cart Application For User Registration System")
@Story("User Story 107: Register Account page for Open Cart Application")

@Listeners({TestAllureListener.class, ExtentReportListener.class})
public class RegisterPageTest extends BaseTest{
	
	@BeforeClass
	public void regSetup() {
		regPage = loginPage.navigateToRegister();
	}
	
	@DataProvider
	public Object[][] userRegTestData() {
		return new Object[][] {
			{"Prakhar", "Automation", "1234567891", "Test@123", "yes"},
			{"Prateek", "Automation", "5892667891", "Test@123", "no"},
			{"Prafull", "Automation", "9814567891", "Test@123", "yes"}
		};
	}
	
	@DataProvider
	public Object[][] userRegTestDataFromSheet() {
		return ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
	}
	
	@Description("Validating user registration test")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Prakhar")
	@Feature("register page feature")
	@Test(dataProvider = "userRegTestDataFromSheet")
	public void userRegistrationTest(String firstname, String lastName, String telephone, String password, String subscribe){
		Assert.assertTrue(regPage.userRegister(firstname, lastName, StringUtils.getRandomEmailId() , telephone, password, subscribe),
				AppError.USER_REG_NOT_DONE);
	}
	
}
