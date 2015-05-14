package com.voluum.page;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import com.selenium.Page;

public class VoluumCampaignsPage extends Page {
	
	private static final String URL_CAMPAIGN_DESTINATION_ADDRESS = "http://example.com?subid={clickId}";
	private static final String CSS_NEW_CAPMAIGN = ".button.button-new";
	private static final String CSS_COUNTRY = "#country";
	private static final String DESTINATION_RADIO_BUTTON_ID = "redirect-target-";
	private static final String GENERATED_CAMPAIGN_URL ="#url";
	private static final String CAMPAIGN_FULL_NAME ="//*[contains (text(),'%s')]";

	@FindBy(css = ".button.button-new")
	private static WebElement newCampaignButton;

	@FindBy(css = "#name-postfix")
	private static WebElement campaignNameField;

	@FindBy(css = ".cancel")
	private static WebElement cancelCampaignButton;

	@FindBy(css = ".submit")
	private static WebElement saveCampaignButton;
	
	@FindBy(css = "#direct-redirect-url")
	private static WebElement campaignDestinationURL;
	
	@FindBy(css = ".button.button-edit")
	private static WebElement editCampaignButton;
	
	public VoluumCampaignsPage(WebDriver driver) {
		super(driver);

	}

	public VoluumCampaignsPage createNewCampaign() {
		fluentWait(By.cssSelector(CSS_NEW_CAPMAIGN));
		newCampaignButton.click();
		return this;
	}

	public VoluumCampaignsPage fillCampaignNameField(String country) {

		selectCountryFromList(country);
		String template = createCampaignName();
		System.out.println(template);
		log.info(template);
		campaignNameField.sendKeys(template);
		Assert.assertNotNull(campaignNameField.getText(),
				"campaign name field should not be blank");
		return this;
	}

	private void selectCountryFromList(String country) {

		log.info("First choose country from the list " + country);
		fluentWait(By.cssSelector(CSS_COUNTRY));
		WebElement select = driver.findElement(By.cssSelector(CSS_COUNTRY));
		List<WebElement> options = select.findElements(By.tagName("option"));
		for (WebElement option : options) {
			if (country.equals(option.getText()))
				option.click();
		}

	}

	private static String createCampaignName() {

		final String name;
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdtf = new SimpleDateFormat("yyyy-MM-dd");
		name = "Selenium New Campaign " + sdtf.format(c.getTime()) + " "
				+ System.currentTimeMillis();
		return name;

	}
	
	/**
	 * 
	 * @param index choose 0 to Path, 1 for URL
	 * @return
	 */
	public VoluumCampaignsPage selectDestination(int index){
		
		if(index == 0 || index == 1){
		String radioButtonID = DESTINATION_RADIO_BUTTON_ID.concat(Integer.toString(index));
		WebElement radioBtn = driver.findElement(By.id(radioButtonID));
		radioBtn.click();
		}
		else Assert.fail("Destination index must be 0 or 1 only");
		
		return this;
	}
	
	public VoluumCampaignsPage fillUrlCampaignDestinationAddress(){
		
		campaignDestinationURL.sendKeys(URL_CAMPAIGN_DESTINATION_ADDRESS);
		return this;
	}

	public VoluumCampaignsPage saveCampaign() {
		saveCampaignButton.click();
		return this;
	}

	public VoluumCampaignsPage cancelCampaignCreation() {

		cancelCampaignButton.click();
		alert.clickAcceptInAlert();
		return this;

	}
	public String getSelectedCampaignUrl(String campaignName){
		
		fluentWait(By.cssSelector(".button.button-edit"));
		final String xPath = String.format(CAMPAIGN_FULL_NAME,campaignName);
		driver.findElement(By.xpath(xPath)).click();
		editCampaignButton.click();
		fluentWait(By.cssSelector(CSS_COUNTRY));
		final String url  =   driver.findElement(By.cssSelector(GENERATED_CAMPAIGN_URL)).getAttribute("value");
		System.out.println(url);
		return url;
	}
}
