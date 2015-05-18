package com.voluum.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.selenium.Page;

public class VoluumBackOfficePage extends Page {
	
	@FindBy(css = ".pill.sign-out")
	private static WebElement logoutButton;
	
	@FindBy(css = ".refresh-button")
	private static WebElement refreshButton;
	
	
	private static String MENU_XPATH ="//*[contains(text(),'%s')]";
	private static String CSS_SIGN_OUT =".pill.sign-out";


	
	public VoluumBackOfficePage(WebDriver driver) {
		super(driver);
	
	}

	public VoluumLoginPage logout(){
	
		fluentWait(By.cssSelector(CSS_SIGN_OUT));	
		logoutButton.click();
		alert.clickAcceptInAlert();
		waitForPageLoad();
		return new VoluumLoginPage(driver);
	}
	
	public void refreshDashboard(){
		refreshButton.click();
		waitForPageLoad();
		
	}
	
	public void clickMenuOption(MenuItemEnum menuOption){
		
		final String xPath = String.format(MENU_XPATH, menuOption.getName());
		driver.findElements(By.xpath(xPath)).get(0).click();
		waitForPageLoad();
	
	}
	
	
}
