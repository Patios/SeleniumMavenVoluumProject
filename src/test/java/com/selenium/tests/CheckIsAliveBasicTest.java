package com.selenium.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.google.common.base.Function;
import com.selenium.DriverFactory;

public class CheckIsAliveBasicTest extends DriverFactory {
	
	public static WebDriver driver;

	private static final String VOLUUM_LOGO_XPATH = "//div[@class='logo']";
	
	@Test
	public void CheckIsAliveTest() {

		driver = getDriver();

		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		System.out.println(cap.getVersion());
		driver.navigate().to("https://www.voluum.com/");

		System.out.println("Page title is: " + driver.getTitle());
		System.out.println("URL: " + driver.getCurrentUrl());
		waitForElementExists(VOLUUM_LOGO_XPATH, 10);


	}

	public static WebElement findDynamicElement(By by, int timeout) {

		WebDriverWait wait = new WebDriverWait(driver, timeout);
		WebElement element = (WebElement) wait.until(ExpectedConditions
				.visibilityOfAllElementsLocatedBy(by));

		return element;
	}

	public static WebElement waitForElementExists(final String selector,
			long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);

		WebElement element = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(By.xpath(selector));
			}
		});

		return element;
	}

}