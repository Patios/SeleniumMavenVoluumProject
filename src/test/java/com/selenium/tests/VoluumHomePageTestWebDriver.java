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
/**
 * @author patryk.nowek
 *
 *         <h1>VoluumHomePageTestWebDriver</h1>
 *         <ul>
 *         <li>Test1 scenario "ShouldOpenVoluumHomePageTest": Simple test to perform visit on home page</li>
 *         <li>Test2 scenario "shouldLoginToBackOfficeAndVisitCampaign": New Campaign schould be able to redirect to Offer URL</li>
 *         <li>Test3 scenario "shouldIncrementCampaignVisit" :HTTP Get request to Campaign URL should result with incrementing campaign visits counter by 1</li>
 *         <li>Test4 scenario "shouldPostbackCampaignURL" : HTTP GET request to Postback URL with valid ClickID token should increment Campaign Conversions count by 1 </li>
 *         </ul>
 */
public class VoluumHomePageTestWebDriver extends DriverFactory {
	
	private String campaignURL;
	private String redirectedURL;
	private VoluumHomePage voluumHomePage;
	private VoluumLoginPage voluumLoginPage;
	private VoluumBackOfficePage voluumBackOfficePage;
	private VoluumCampaignsPage voluumCampaignsPage;
	private int previousConversionCounter;
	private int currentConversionCounter;
	private static String postbackURL = "http://7ctnf.voluumtrk.com/postback?cid=%s";
	private static final int CID_LENGTH = 24;
	private static final String COUNTRY = "Poland";
	private static int previousVisitCounter , currentVisitCounter;


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
	public void shouldOpenVoluumHomePageTest() {

		voluumHomePage.acceptCookie();
		voluumHomePage.openHomePage();
		voluumHomePage.clickLoginButton();
		setExplicitWait(".right>a", 20);
		voluumLoginPage.backToVoluumHomePage();
		voluumHomePage.openHomePage();

	}
	/**
	 * Login to backOffice page and create campaign unless it was not created previously.
	 * 
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
		//edit existing campaign
		String campaignName = voluumCampaignsPage.findDisplayedElements().get(0).getText();
		campaignURL = voluumCampaignsPage.getSelectedCampaignUrl(campaignName);
		navigateToUrl(campaignURL);
		redirectedURL = getCurrentUrl();
		Assert.assertEquals(redirectedURL.substring(26).length(), CID_LENGTH,"Subid should has 24 characters!");
		

	}
	/**
	 * should result with incrementing campaign visit counter by 1
	 */
	@Test(priority = 3, dependsOnMethods={"shouldLoginToBackOfficeAndVisitCampaign"}, enabled = true)
	public void shouldIncrementCampaignVisit(){
	
		voluumHomePage.openHomePage();
		voluumHomePage.clickLoginButton();
		setExplicitWait(".pill.sign-out");
		voluumBackOfficePage.clickMenuOption(MenuItemEnum.CAMPAIGNS);
		wait(5000);
		previousVisitCounter = 	voluumCampaignsPage.getCampaignVisitValue();
		navigateToUrl(campaignURL);
		voluumHomePage.openHomePage();
		voluumHomePage.clickLoginButton();
		voluumBackOfficePage.clickMenuOption(MenuItemEnum.CAMPAIGNS);
		wait(5000);
		currentVisitCounter = voluumCampaignsPage.getCampaignVisitValue();
		for (int i = 1; i < 10; i++) {
			if (currentVisitCounter == previousVisitCounter) {
				System.out.println(i + " Attempt to get increased visit value");
				voluumBackOfficePage.refreshDashboard();
				wait(1000);
				currentVisitCounter = voluumCampaignsPage.getCampaignVisitValue();
				System.out.println("Current Visit value is: " + currentConversionCounter);
			} 
		}
		Assert.assertEquals(currentVisitCounter, previousVisitCounter + 1, "Visit counter after performing campaign visit should increase by 1 !");
		voluumBackOfficePage.logout();
	
	}
	
	/**
	 * Invoke postback action on previously created campaign in testcase no.2
	 * Opening campaign URL with cid parameter.
	 */
	@Test(priority = 4, dependsOnMethods={"shouldLoginToBackOfficeAndVisitCampaign"}, enabled = true)
	public void shouldPostbackCampaignURL(){
		
		voluumHomePage.openHomePage();
		voluumHomePage.clickLoginButton();
		setExplicitWait("#username");
		voluumLoginPage.setLogin(User.getName()).setPassword(User.getPassword()).Login();
		setExplicitWait(".pill.sign-out");
		voluumBackOfficePage.clickMenuOption(MenuItemEnum.CAMPAIGNS);
		wait(5000);
		previousConversionCounter = voluumCampaignsPage.getCampaignConversionsValue();
		navigateToUrl(redirectedURL);
		String cid = redirectedURL.substring(26);
		System.out.println("Cid: "+cid);
		System.out.println(String.format(postbackURL, cid));
		navigateToUrl(String.format(postbackURL, cid));
		voluumHomePage.openHomePage();
		voluumHomePage.clickLoginButton();
		setExplicitWait(".pill.sign-out");
		voluumBackOfficePage.clickMenuOption(MenuItemEnum.CAMPAIGNS);
		wait(5000);
		currentConversionCounter = voluumCampaignsPage.getCampaignConversionsValue();
		for (int i = 1; i < 30; i++) {
			if (currentConversionCounter == previousConversionCounter) {
				System.out.println(i + " Attempt");
				voluumBackOfficePage.refreshDashboard();
				wait(1000);
				currentConversionCounter = voluumCampaignsPage.getCampaignConversionsValue();
				System.out.println("Current conversion value is: " + currentConversionCounter);
			} 
		}
		Assert.assertEquals(currentConversionCounter, previousConversionCounter + 1, "Conversion counter after invoking postback action should increase by 1 !");
		voluumBackOfficePage.logout();
	
	}
}
