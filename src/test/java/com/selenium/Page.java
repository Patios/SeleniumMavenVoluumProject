package com.selenium;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;


public class Page {
	
	protected static final int PAGE_LOAD_TIMEOUT = 15000;
	protected final WebDriver driver;

	
	public Page(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		}
	
	public Page(WebDriver driver, String url) {
		this.driver = driver;
		if (isNotBlank(url)) {
			driver.navigate().to(url);
		}
	}

	private boolean isNotBlank(String url) {
		return StringUtils.isNotBlank(url);
	}
	
	

}
