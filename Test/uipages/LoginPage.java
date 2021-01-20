package uipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utilities.BaseUtilities;

public class LoginPage {

	AndroidDriver<WebElement>  driver; 

	public LoginPage(AndroidDriver<WebElement> driver) {
		this.driver=driver;

	}

	private By selectEnglish=MobileBy.xpath("//*[@text='English']");
	private By mainContinueButton=MobileBy.xpath("//*[@text='CONTINUE']");
	private By continuebutton=MobileBy.xpath("//*[@text='Continue']");
	private By useEmailID=MobileBy.xpath("//*[@text='Use Email-ID']");
	private By enterEmailID=MobileBy.AccessibilityId("Email ID");
	private By enterPassword=MobileBy.id("com.flipkart.android:id/phone_input");
	private By searchTextBox=MobileBy.xpath("//*[contains(@text,'Search for Products')]");

	public LoginPage login(String email,String password) {

		BaseUtilities.clickElement(driver, selectEnglish);
		BaseUtilities.clickElement(driver, mainContinueButton);
		BaseUtilities.clickElement(driver, useEmailID);
		BaseUtilities.setValue(driver, enterEmailID, email);
		BaseUtilities.tapByElement(driver, continuebutton);
		BaseUtilities.setValue(driver, enterPassword,BaseUtilities.decoder(password));
		BaseUtilities.tapByElement(driver, continuebutton);


		return this;
	}

	public boolean verifyLogin() {
		return BaseUtilities.verifyElementPresent(driver, searchTextBox);
	}


}