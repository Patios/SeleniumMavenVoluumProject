package com.voluum.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.selenium.Page;

public class VoluumLoginPage extends Page {
	
	
	@FindBy(css = "#username")
	private static WebElement userNameField;
	
	@FindBy(css = "#password")
	private static WebElement passwordNameField;
	
	@FindBy(css = "#submit")
	private static WebElement loginButton;
	
	
	private static final String CSS_BACK_TO_HOME_PAGE = ".right>a";
	
	public VoluumLoginPage(WebDriver driver) {
		super(driver);
		
	
	}
	
	public VoluumHomePage backToVoluumHomePage(){
		
		driver.findElement(By.cssSelector(CSS_BACK_TO_HOME_PAGE)).click();
		
		return new VoluumHomePage(driver);
	}
	
	public VoluumLoginPage setLogin(String emailAddress){
		
		userNameField.clear();
		userNameField.sendKeys(emailAddress);
		return this;
	}
	
	public VoluumLoginPage setPassword(String password){
		
		passwordNameField.clear();
		passwordNameField.sendKeys(password);
		return this;
	}
	
	public VoluumBackOfficePage Login(){
			
		loginButton.click();
		return new VoluumBackOfficePage(driver);
		
	}

}
