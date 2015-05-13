package com.voluum.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.selenium.Page;

public class VoluumHomePage extends Page {

	private static final String HOMEPAGE_LINK = "https://voluum.com/";
	private static final String CSS_AGREE_COOKIES = ".agreeCookies";
	private static final String CSS_LOGIN_BUTTON = ".login-button.button-with-shadow";
	private String url;

	public VoluumHomePage(WebDriver driver) {
		super(driver);

	}

	public VoluumHomePage(WebDriver driver, String url) {
		super(driver);
		this.url = url;

	}
	
	public void refreshPage(){
		driver.navigate().to(HOMEPAGE_LINK);
		
	}
	
	public void acceptCookie(){
		driver.findElement(By.cssSelector(CSS_AGREE_COOKIES)).click();
	}
	
	public VoluumLoginPage clickLoginButton(){
		driver.findElement(By.cssSelector(CSS_LOGIN_BUTTON)).click();
		return new VoluumLoginPage(driver);
	}
	
	
}
