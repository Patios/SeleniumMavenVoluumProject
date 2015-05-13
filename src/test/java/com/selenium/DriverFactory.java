package com.selenium;

import static com.selenium.config.DriverType.determineEffectiveDriverType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import com.selenium.config.DriverType;
import com.selenium.listeners.ScreenshotListener;

@Listeners(ScreenshotListener.class)
public class DriverFactory {

	private static Log log = LogFactory.getLog(DriverFactory.class);
	private static List<WebDriver> webDriverPool = Collections.synchronizedList(new ArrayList<WebDriver>());
	private static ThreadLocal<WebDriver> driverThread;

	public DriverFactory() {
		setBinaryVariables();
	}
	
	protected void setUpTest(){};

	private void setBinaryVariables() {
		for (DriverType driverType : DriverType.values()) {
			String variable = driverType.getWebDriverSystemPropertyKey();
			if (null != variable) {
				String systemProperty = System.getProperty(variable);
				if (null == systemProperty || systemProperty.isEmpty()) {
					String environmentalVariable = System.getenv(variable);
					if (null != environmentalVariable
							&& !environmentalVariable.isEmpty()) {
						System.setProperty(variable, environmentalVariable);
					}
				}
			}
		}
	}

	@BeforeSuite
	public static void instantiateDriverObject() {

		final DriverType desiredDriver = determineEffectiveDriverType(System
				.getProperty("browser"));

		driverThread = new ThreadLocal<WebDriver>() {
			@Override
			protected WebDriver initialValue() {
				final WebDriver webDriver = desiredDriver
						.configureDriverBinaryAndInstantiateWebDriver();
				webDriverPool.add(webDriver);
				return webDriver;
			}
		};
	}

	public static WebDriver getDriver() {
		return driverThread.get();
	}

	@AfterMethod
	public static void clearCookies() {
		getDriver().manage().deleteAllCookies();
	}

	@AfterSuite
	public static void closeDriverObject() {
		for (WebDriver driver : webDriverPool) {
			driver.quit();
		}log.info("END OF SELENIUM TESTES");
	}
}