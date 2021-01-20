package uipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import utilities.BaseUtilities;

public class HomePage {


	AndroidDriver<WebElement>  driver; 

	public HomePage(AndroidDriver<WebElement> driver) {
		this.driver=driver;

	}

	private By searchTextBox=MobileBy.xpath("//*[contains(@text,'Search for Products')]");
	private String xpathForItem="//*[@text='%s' and @resource-id='com.flipkart.android:id/txt_title']";
	private By searchList=MobileBy.className("android.widget.ScrollView");
	private String searchResultClass="android.widget.ScrollView";
	private By notNowButton=MobileBy.id("com.flipkart.android:id/not_now_button");
	private By addToCart=MobileBy.xpath("//*[@text='ADD TO CART']");
	private By productTitle=MobileBy.xpath("(//android.widget.TextView)[2]");

	public HomePage searchItem(String item) {
		BaseUtilities.clickElement(driver, searchTextBox);
		BaseUtilities.setValue(driver, searchTextBox, item);
		BaseUtilities.clickElementUsingTextinXpath(driver, xpathForItem,item.toLowerCase());
		BaseUtilities.clickElement(driver, notNowButton);

		return this;
	}

	public boolean verifySearchResults() {
		return BaseUtilities.verifyElementPresent(driver, searchList);
	}

	public HomePage clickOnSearchResult(String item) {
		BaseUtilities.scrollToChildInParentByText(driver, searchResultClass, item);
		BaseUtilities.clickElementByText(driver, item);		
		return this;
	}

	public boolean verifyProductOpened(String item) {
		String itemTitle=BaseUtilities.getText(driver, productTitle);
		if(BaseUtilities.verifyElementPresent(driver, addToCart) && itemTitle.toLowerCase().contains(item.toLowerCase())) {
			return true;
		}
		else {
			return false;
		}

	}

}
