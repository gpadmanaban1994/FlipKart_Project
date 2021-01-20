package uipages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.internal.Utils;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import utilities.BaseUtilities;

public class ProductPage {

	AndroidDriver<WebElement> driver;
	public ProductPage(AndroidDriver<WebElement> driver) {
		this.driver = driver;
	}

	private By detailsIcon=By.xpath("//*[@text='Details']");
	private By addToCart=MobileBy.xpath("//*[@text='ADD TO CART']");
	private By productDetails=MobileBy.xpath("//android.widget.TextView");
	private By productDetailsHeader=MobileBy.xpath("//*[@text='Product Details']");
	private By goToCart=MobileBy.xpath("//*[@text='GO TO CART']");
	private By toastMessage=By.xpath("//android.widget.Toast[1]')]");
	private By productDetailsImage=By.xpath("(//android.widget.ImageView)[3]");

	public ProductPage clickOnDetails() {
		driver.findElement(detailsIcon).click();
		return this;
	}

	public boolean openProductDetails() {
		if(BaseUtilities.verifyElementPresent(driver, productDetailsHeader)) {
			if(BaseUtilities.verifyElementPresent(driver, productDetailsImage)){
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}

	public List getProductDetails() {

		List<WebElement> productDetailsList=driver.findElements(productDetails); 
		List<String> details=new ArrayList<String>();
		details.add(productDetailsList.get(1).getText().trim());
		details.add(productDetailsList.get(2).getText().trim());
		driver.pressKey(new KeyEvent(AndroidKey.BACK));
		return details;
	}

	public boolean clickAddToCart() throws InterruptedException {
		BaseUtilities.tapByElement(driver, addToCart);
		if(BaseUtilities.verifyElementPresent(driver, goToCart)) {
			Thread.sleep(500);
			return true;
		}
		else {
			return false;
		}

	}

	public boolean clickGoToCart() {
		if(BaseUtilities.clickElement(driver, goToCart)) {
			return true;	
		}
		else {
			return false;
		}
	}
}
