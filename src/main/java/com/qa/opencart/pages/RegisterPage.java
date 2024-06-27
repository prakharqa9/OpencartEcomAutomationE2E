package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

import io.qameta.allure.Step;

public class RegisterPage {
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	//1. Page Objects: By Locators:
	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By confirmPassword = By.id("input-confirm");
	
	private By subscribeYes = By.xpath("//label[@class='radio-inline'][position()=1]/input[@type='radio']");
	private By subscribeNo = By.xpath("//label[@class='radio-inline'][position()=2]/input[@type='radio']");
	
	private By agreeCheckBox = By.name("agree");
	private By continueButton = By.xpath("//input[@type='submit' and @value='Continue']");
	
	private By successMessg = By.cssSelector("div#content h1");
	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");
	
	// 2. public constructor of the page
	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	@Step("Registering user: [first name : {0}] -- [last name : {1}] -- [email : {2}] -- [telephone : {3}]  -- [subscribe : {5}] ")
	public boolean userRegister(String firstName, String lastName, String email, String telephone,
			String password, String subscribe){
		eleUtil.doSendKeys(this.firstName, firstName, TimeUtil.DEFAULT_MEDIUM_TIME);
		eleUtil.doSendKeys(this.lastName, lastName);
		eleUtil.doSendKeys(this.email, email);
		eleUtil.doSendKeys(this.telephone, telephone);
		eleUtil.doSendKeys(this.password, password);
		eleUtil.doSendKeys(this.confirmPassword, password);
		
		if(subscribe.equals("yes")) {
			eleUtil.doClick(subscribeYes);
		}else {
			eleUtil.doClick(subscribeNo);
		}
		
		eleUtil.doClick(agreeCheckBox);
		eleUtil.doClick(continueButton);
		
		String successMesg = eleUtil.waitForElementVisible(successMessg, TimeUtil.DEFAULT_LONG_TIME).getText();
		System.out.println(successMesg);
		
		if(successMesg.contains(AppConstants.USER_REGISTER_SUCCESS_MESSAGE)) {
			eleUtil.doClick(logoutLink, TimeUtil.DEFAULT_MEDIUM_TIME);
			eleUtil.doClick(registerLink, TimeUtil.DEFAULT_MEDIUM_TIME);
			//eleUtil.doClick(logoutLink);
			//eleUtil.doClick(registerLink);
			return true;
		}else {
			return false;
		}
		
	}
	
}




















