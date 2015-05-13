package com.selenium.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.selenium.DriverFactory;
import com.voluum.page.MenuItemEnum;
import com.voluum.page.VoluumBackOfficePage;
import com.voluum.page.VoluumHomePage;
import com.voluum.page.VoluumLoginPage;

public class VoluumHomePageTestWebDriver extends DriverFactory {

	VoluumHomePage voluumHomePage;
	VoluumLoginPage voluumLoginPage;
	VoluumBackOfficePage voluumBackOfficePage;

	@Override
	protected void setUpTest() {

	}

	@BeforeClass
	public void initialize() {
		voluumHomePage = new VoluumHomePage(getDriver(), "http://voluum.com");
		voluumLoginPage = new VoluumLoginPage(getDriver());
		voluumBackOfficePage = new VoluumBackOfficePage(getDriver());

	}

	@Test(priority = 1, enabled = true)
	public void ShouldOpenVoluumHomePageTest() {

		voluumHomePage.acceptCookie();
		voluumHomePage.refreshPage();
		voluumHomePage.clickLoginButton();
		setExplicitWait(".right>a", 20);
		voluumLoginPage.backToVoluumHomePage();
		voluumHomePage.refreshPage();

	}

	@Test(priority = 2, enabled = true)
	public void shouldLoginToBackOffice() {

		voluumHomePage.clickLoginButton();
				setExplicitWait("#username");
		voluumLoginPage.setLogin("patios18@gmail.com")
				.setPassword("halflife")
				.Login();
		// wait needs to be added here!!
		setExplicitWait(".pill.sign-out");
		// experimental
		voluumBackOfficePage.clickMenuOption(MenuItemEnum.CAMPAIGNS);
		wait(5000);
		voluumBackOfficePage.logout();

	}

}
