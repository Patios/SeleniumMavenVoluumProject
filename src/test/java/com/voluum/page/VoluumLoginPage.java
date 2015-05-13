package com.voluum.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.selenium.Page;

public class VoluumLoginPage extends Page {

	private static final String CSS_BACK_TO_HOME_PAGE = ".right>a";
	
	public VoluumLoginPage(WebDriver driver) {
		super(driver);
	
	}
	
	public VoluumHomePage backToVoluumHomePage(){
		
		driver.findElement(By.cssSelector(CSS_BACK_TO_HOME_PAGE)).click();
		
		return new VoluumHomePage(driver);
	}
	
	
	

}
