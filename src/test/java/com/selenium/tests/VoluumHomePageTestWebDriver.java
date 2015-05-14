package com.selenium.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.selenium.DriverFactory;
import com.voluum.page.MenuItemEnum;
import com.voluum.page.VoluumBackOfficePage;
import com.voluum.page.VoluumCampaignsPage;
import com.voluum.page.VoluumHomePage;
import com.voluum.page.VoluumLoginPage;

public class VoluumHomePageTestWebDriver extends DriverFactory {
	
	String redirectedURL;
	VoluumHomePage voluumHomePage;
	VoluumLoginPage voluumLoginPage;
	VoluumBackOfficePage voluumBackOfficePage;
	VoluumCampaignsPage voluumCampaignsPage;
	private static String postbackURL = "http://7ctnf.voluumtrk.com/postback?cid=%s";

	@Override
	protected void setUpTest() {

	}

	@BeforeClass
	public void initialize() {
		voluumHomePage = new VoluumHomePage(getDriver(), "http://voluum.com");
		voluumLoginPage = new VoluumLoginPage(getDriver());
		voluumBackOfficePage = new VoluumBackOfficePage(getDriver());
		voluumCampaignsPage = new VoluumCampaignsPage(getDriver());

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
		voluumLoginPage.setLogin("patios18@gmail.com").setPassword("halflife").Login();
		setExplicitWait(".pill.sign-out");
		voluumBackOfficePage.clickMenuOption(MenuItemEnum.CAMPAIGNS);
		
		//edit exisiting campaign
		String campaignURL = voluumCampaignsPage.getSelectedCampaignUrl("ZeroPark - Poland - Selenium New Campaign 2015-05-14 1431608737454");
		navigateToUrl(campaignURL);
		redirectedURL = getCurrentUrl();
		System.out.println(redirectedURL);
		
		//createNewCapaign
//		voluumCampaignsPage.createNewCampaign()	
//						   .fillCampaignNameField("Poland")
//						   .selectDestination(1)
//						   .fillUrlCampaignDestinationAddress()
//						   .saveCampaign();
//		voluumBackOfficePage.logout();
	}
	
	@Test(priority=3, enabled = true)
	public void shouldPostbackCampaignURL(){
		
		navigateToUrl(redirectedURL);
		waitForPageLoad();
		
		String cid = redirectedURL.substring(26);
		//to remove
		System.out.println("Cid: "+cid);
		//
		
		//to remove
		System.out.println(String.format(postbackURL, cid));
		//
		navigateToUrl(String.format(postbackURL, cid));
		waitForPageLoad();
	
	}
}
