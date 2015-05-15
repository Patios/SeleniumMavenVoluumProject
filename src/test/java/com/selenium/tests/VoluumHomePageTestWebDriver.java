package com.selenium.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.selenium.DriverFactory;
import com.selenium.User;
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
	private static final int CID_LENGTH = 24;
	private static final String COUNTRY = "Poland";
	private static int previosuVisitCounter , currentVisitCounter;

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
	/**
	 * Simple test to check is service is accessible. 
	 * Opening voluum.com homePage and accepting cookies policy.
	 */
	@Test(priority = 1, enabled = true)
	public void ShouldOpenVoluumHomePageTest() {

		voluumHomePage.acceptCookie();
		voluumHomePage.refreshPage();
		voluumHomePage.clickLoginButton();
		setExplicitWait(".right>a", 20);
		voluumLoginPage.backToVoluumHomePage();
		voluumHomePage.refreshPage();

	}
	/**
	 * Login to backOffice page and create campaign unless it was not created previously.
	 */
	@Test(priority = 2, enabled = true)
	public void shouldLoginToBackOfficeAndVisitCampaign() {

		voluumHomePage.clickLoginButton();
		setExplicitWait("#username");
		voluumLoginPage.setLogin(User.getName()).setPassword(User.getPassword()).Login();
		setExplicitWait(".pill.sign-out");
		voluumBackOfficePage.clickMenuOption(MenuItemEnum.CAMPAIGNS);
		wait(5000);
		if(voluumCampaignsPage.findDisplayedElements().isEmpty()){
			//create new campaign
			voluumCampaignsPage.createNewCampaign()	
			.fillCampaignNameField(COUNTRY)
			.selectDestination(1)
			.fillUrlCampaignDestinationAddress()
			.saveCampaign();
			setImplicitlyWait(5);
			}
		//edit exisiting campaign
		String campaignName = voluumCampaignsPage.findDisplayedElements().get(0).getText();
		String campaignURL = voluumCampaignsPage.getSelectedCampaignUrl(campaignName);
		navigateToUrl(campaignURL);
		redirectedURL = getCurrentUrl();
		Assert.assertEquals(redirectedURL.substring(26).length(), CID_LENGTH,"subid has not have 24 characters!");
		

	}
	/**
	 * should result with incrementing campaign visit counter by 1
	 */
	@Test(priority = 3, dependsOnMethods={"shouldLoginToBackOfficeAndVisitCampaign"}, enabled = true)
	public void shouldIncrementCampaignVisit(){
	
		voluumHomePage.clickLoginButton();
		setExplicitWait("#username");
		voluumLoginPage.setLogin(User.getName()).setPassword(User.getPassword()).Login();
		setExplicitWait(".pill.sign-out");
		voluumBackOfficePage.clickMenuOption(MenuItemEnum.CAMPAIGNS);
		wait(5000);
		
		previosuVisitCounter = 1; // TODO getVisitCounter method returning int here !!!
		navigateToUrl(redirectedURL);
		waitForPageLoad();
		voluumHomePage.clickLoginButton();
		waitForPageLoad();
		voluumBackOfficePage.clickMenuOption(MenuItemEnum.CAMPAIGNS);
		wait(10000); 
		currentVisitCounter = +1 ;

		
	}
	
	/**
	 * Invoke postback action on previously created campaign in testcase no.2
	 * Opening campaign URL with cid parameter.
	 */
	@Test(priority = 4, dependsOnMethods={"shouldLoginToBackOfficeAndVisitCampaign"}, enabled = true)
	public void shouldPostbackCampaignURL(){
		
		navigateToUrl(redirectedURL);
		waitForPageLoad();
		String cid = redirectedURL.substring(26);
		//to remove
		System.out.println("Cid: "+cid);
		//to remove
		System.out.println(String.format(postbackURL, cid));
		navigateToUrl(String.format(postbackURL, cid));
		waitForPageLoad();
		//TODO
		// Add checking postback  in campaing conversions = 1 
	
	}
}
