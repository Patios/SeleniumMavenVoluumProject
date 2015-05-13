package com.voluum.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.selenium.Page;

public class VoluumBackOfficePage extends Page {
	
	@FindBy(css = ".pill.sign-out")
	
	private static WebElement logoutButton;

	public VoluumBackOfficePage(WebDriver driver) {
		super(driver);
		

	}

	public VoluumLoginPage logout(){
		
		logoutButton.click();
		
		return new VoluumLoginPage(driver);
	}
	
}
