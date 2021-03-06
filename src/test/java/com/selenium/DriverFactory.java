package com.selenium;

import static com.selenium.config.DriverType.determineEffectiveDriverType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.log4testng.Logger;

import com.selenium.config.DriverType;
import com.selenium.listeners.ScreenshotListener;

@Listeners(ScreenshotListener.class)
public class DriverFactory {

	private static Log log = LogFactory.getLog(DriverFactory.class);
	private static List<WebDriver> webDriverPool = Collections.synchronizedList(new ArrayList<WebDriver>());
	private static ThreadLocal<WebDriver> driverThread;
	private static WebElement myDynamicElement;

	public DriverFactory() {
		setBinaryVariables();
	}
	
	private static void setUpTestAndMaximizeBrowserWindow(){
		getDriver().manage().window().maximize();
	};

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
		setUpTestAndMaximizeBrowserWindow();
		
		
	}

	public static WebDriver getDriver() {
		return driverThread.get();
	}
	
    protected static void wait(int miliseconds) {
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException e) {
            log.error("Thread interrupted. It's over.");
        }
    }
    
    protected  void setImplicitlyWait(int t){
    	getDriver().manage().timeouts().implicitlyWait(t, TimeUnit.SECONDS);
    }
    
    public  void navigateToUrl(String url){
    	getDriver().navigate().to(url);
    	setImplicitlyWait(5);
    	
    }
    
    public static String getCurrentUrl(){
    	String currentURL = null;
    	WebDriverWait wait = new WebDriverWait(getDriver(), 10);
    	   ExpectedCondition<Boolean> e = new ExpectedCondition<Boolean>() {
    	          public Boolean apply(WebDriver d) {
    	            return (d.getCurrentUrl() != null);
    	          }
    	        };
    	wait.until(e);
    	currentURL = getDriver().getCurrentUrl();
       	return currentURL;
    }
    
    protected void setExplicitWait(String cssSelector){
    	myDynamicElement = new WebDriverWait(getDriver(), 20).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelector)));
    }

    protected void setExplicitWait(String cssSelector, int timeInSeconds){
    	myDynamicElement = new WebDriverWait(getDriver(), timeInSeconds).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelector)));
    }
    
	@AfterMethod
	public static void clearCookies() {
		getDriver().manage().deleteAllCookies();
	}
	/**
	 * Kill driver instance after last test.
	 */
	@AfterSuite
	public static void closeDriverObject() {
		for (WebDriver driver : webDriverPool) {
			driver.quit();
		}log.info("END OF SELENIUM TESTES");
	}
}