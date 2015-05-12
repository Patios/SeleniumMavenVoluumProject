package com.lazerycode.selenium.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.google.common.base.Function;
import com.lazerycode.selenium.DriverFactory;

public class Selenium2ExampleWebDriver extends DriverFactory {

	public static WebDriver driver;

	private static final String VOLUUM_LOGO_XPATH = "//div[@class='logo']";

	@Test
	public void MyGoogleTest() {
		// Create a new instance of the Firefox driver
		// Notice that the remainder of the code relies on the interface,
		// not the implementation.

		driver = getDriver();

		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		System.out.println(cap.getVersion());

		// And now use this to visit Google
		driver.get("http://google.com");
		driver.getCurrentUrl();
		// Alternatively the same thing can be done like this
		// driver.navigate().to("http://www.google.com");

		// Find the text input element by its name
		WebElement element = driver.findElement(By.name("q"));

		// Enter something to search for
		element.sendKeys("Cheese!");

		// Now submit the form. WebDriver will find the form for us from the
		// element
		element.submit();

		// Check the title of the page
		System.out.println("Page title is: " + driver.getTitle());

		// Google's search is rendered dynamically with JavaScript.
		// Wait for the page to load, timeout after 10 seconds
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.getTitle().toLowerCase().startsWith("cheese!");
			}
		});

		// Should see: "cheese! - Google Search"
		System.out.println("Page title is: " + driver.getTitle());
		System.out.println("URL: " + driver.getCurrentUrl());
		System.out.println("try to not quit until click");

		driver.navigate().to("https://voluum.com/");

		waitForElementExists(VOLUUM_LOGO_XPATH, 30);

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