package uipages;

import java.util.ArrayList;
import java.util.List;import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.android.AndroidDriver;
import utilities.BaseUtilities;

public class CheckOutPage {
	AndroidDriver<WebElement> driver;

	public CheckOutPage(AndroidDriver<WebElement> driver) {
		this.driver = driver;
	}


	private By cartImage=By.xpath("(//android.widget.ImageView)[2]");
	private By cartTitle=By.xpath("//*[@text='My Cart']");
	private By cartDetailsList=By.className("android.widget.TextView");
	private By QuantityDropdown=By.xpath("//*[contains(@text,'Qty')]");
	private By selectQuantityDropdown=By.xpath("//android.widget.ScrollView");
	private String selectQuantity=	"//android.widget.ScrollView//*[contains(@text,'%s')]";
	private By removeButton=By.xpath("//*[contains(@text,'Remove')]");
	private By removeConfirmBox=By.xpath("//*[contains(@text,'Are you sure')]");
	private By quantityToast=By.xpath("(//android.widget.Toast)[1]");

	public boolean verifyCartPageIsOpened() {
		if(BaseUtilities.verifyElementPresent(driver, cartImage) && BaseUtilities.verifyElementPresent(driver, cartTitle)) {
			return true;
		}
		else {
			return false;
		}
	}

	public List getProductDetailsFromCart() {
		List<WebElement> productDetailsFromCart=driver.findElements(cartDetailsList);
		List<String> detailsDromCart=new ArrayList<String>();
		for(int i=0;i<productDetailsFromCart.size();i++) {
			detailsDromCart.add(productDetailsFromCart.get(i).getText().trim());
		}
		return detailsDromCart;
	}
	public boolean selectProductQuantity(String quantity) {
		boolean flag=false;
		BaseUtilities.clickElement(driver, QuantityDropdown);
		if(BaseUtilities.verifyElementPresent(driver,selectQuantityDropdown)) {
			BaseUtilities.clickElementUsingTextinXpath(driver, selectQuantity, quantity);
			if(StringUtils.isNotBlank(BaseUtilities.getToastMessages(driver,quantityToast))
					&& BaseUtilities.getToastMessages(driver,quantityToast).contains(quantity) ){			
				flag= true;
			}
		}
		return flag;
	}

	public boolean removeItemBeforeUnisntalling() {
		boolean flag=false;
		if(BaseUtilities.clickElement(driver, removeButton)) {
			if(BaseUtilities.verifyElementPresent(driver, removeConfirmBox)) {
				if(	BaseUtilities.clickElementFromList(driver, removeButton, 1)) {

					flag=true;
				}
			}
		}
		return flag;
	}


}
