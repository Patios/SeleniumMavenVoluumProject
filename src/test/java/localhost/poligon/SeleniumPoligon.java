package localhost.poligon;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.google.common.base.Function;

public class SeleniumPoligon {

	public static WebDriver driver;
	public static WebElement element;
	public static final String PAGE_URL="http://www.voluum.com";

	@Test
	public void testOverall() {

		driver = new FirefoxDriver();

		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		System.out.println(cap.getVersion());
		System.out.println("Page title is: " + driver.getTitle());

		driver.get(PAGE_URL);

		try {
			element = driver.findElement(By.cssSelector(".agreeCookies"));
			element.click();

		} catch (Exception e) {
			System.out.println("Unable to click cookie banner");
		}
		
		driver.navigate().to(PAGE_URL);
		
		System.out.println("Page title is: " + driver.getTitle());
		System.out.println("URL: " + driver.getCurrentUrl());

		waitForElementExists("//div[@class='logo']", 10);

		// Close the browser

		driver.quit();
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