package com.selenium;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;


public class AlertHandler {

	private WebDriver driver;

	public AlertHandler(WebDriver driver) {
		this.driver = driver;
	}

	public Alert getAlertIfPresent() {
		Alert alert = null;
		try {
			alert = driver.switchTo().alert();
		} catch (NoAlertPresentException e) {
		} catch(NoSuchWindowException e){			
		}
		return alert;
	}

	public String getAlertMessage() {
		Alert alert = getAlertIfPresent();
		if (alert != null) {
			return alert.getText();
		} else {
			return null;
		}
	}

	public void clickAcceptInAlert() {
		Alert alert = getAlertIfPresent();
		if (alert != null) {
			alert.accept();
		}
	}

	public void clickDismissInAlert() {
		Alert alert = getAlertIfPresent();
		if (alert != null) {
			alert.dismiss();
		}
	}

	public void waitForAlertToAppear() {
		(new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				Alert alert = null;
				while (alert == null) {
					alert = driver.switchTo().alert();
				}
				return true;
			}
		});
	}

	public String getAlertMessageAndClickAccept(){
		String message = getAlertMessage();
		clickAcceptInAlert();
		return message;
	}
}
