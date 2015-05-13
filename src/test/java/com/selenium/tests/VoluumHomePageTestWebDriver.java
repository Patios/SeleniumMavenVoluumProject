package com.selenium.tests;

import org.testng.annotations.Test;

import com.selenium.DriverFactory;
import com.voluum.page.VoluumHomePage;
import com.voluum.page.VoluumLoginPage;

public class VoluumHomePageTestWebDriver extends DriverFactory{

	
	VoluumHomePage voluumHomePage;
	VoluumLoginPage voluumLoginPage;
	
	
	@Test
	public void ShouldOpenVoluumHomePageTest(){
		
		voluumHomePage = new VoluumHomePage(getDriver());
		voluumHomePage.acceptCookie();
		voluumHomePage.refreshPage();
		voluumHomePage.clickLoginButton();
		
		voluumLoginPage = new VoluumLoginPage(getDriver());
		voluumLoginPage.backToVoluumHomePage();
		voluumHomePage.refreshPage();
		
	}
	
	
}
