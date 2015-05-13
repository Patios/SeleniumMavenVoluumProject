package com.voluum.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.selenium.Page;

public class VoluumBackOfficePage extends Page {
	
	@FindBy(css = ".pill.sign-out")
	private static WebElement logoutButton;
	
	private static String MENU_XPATH ="//*[contains(text(),'%s')]";
	
	

	public static void setLogoutButton(WebElement logoutButton) {
		VoluumBackOfficePage.logoutButton = logoutButton;
	}

	
	public VoluumBackOfficePage(WebDriver driver) {
		super(driver);
		

	}

	public VoluumLoginPage logout(){
		
		logoutButton.click();
		return new VoluumLoginPage(driver);
	}
	
	public void clickMenuOption(MenuItemEnum menuOption){
		
		final String xPath = String.format("//*[contains(text(),'%s')]", menuOption.getName());
		driver.findElements(By.xpath(xPath)).get(0).click();
		
	}
	
	
	
}
