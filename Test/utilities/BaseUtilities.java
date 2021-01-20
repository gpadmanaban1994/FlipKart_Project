package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.utils.FileUtil;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.functions.ExpectedCondition;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;


public class BaseUtilities {



	//Takes the screenshot and returns the mediaentity to be used in the extent report log for attaching that screenshot
	public static MediaEntityModelProvider captureScreenShot(WebDriver driver) throws IOException {
		Date date=new Date();
		String time=new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss").format(date);
		TakesScreenshot ts=(TakesScreenshot)driver;
		File file=ts.getScreenshotAs(OutputType.FILE);
		File dest=new File(System.getProperty("user.dir")+"//screenshots//"+time+".png");
		FileUtils.copyFile(file, dest);
		MediaEntityModelProvider media= MediaEntityBuilder.createScreenCaptureFromPath(dest.getAbsolutePath()).build();
		return media;
	}

	public static boolean clickElement(WebDriver driver,By findBy) {
		if(verifyElementPresent(driver, findBy)) {
				driver.findElement(findBy).click();
				return true;
		}
		else {
			return false;
		}
	}

	public static boolean clickElementFromList(WebDriver driver,By findBy,int eleIndex) {
		if(verifyElementPresent(driver, findBy)) {
				driver.findElements(findBy).get(eleIndex).click();
				return true;
		}
		else {
			return false;
		}
	}
	public static void clickElementByText(WebDriver driver, String text) {
		driver.findElement(By.xpath(String.format("//*[contains(@text,'%s')]",text))).click();
	}
	public static void clickElementUsingTextinXpath(WebDriver driver,String xpath,String text) {
		driver.findElement(MobileBy.xpath(String.format(xpath, text))).click();
	}
	public static void scrollToChildInParentByText(AndroidDriver<WebElement> driver,String parentClassName,String childText) {
		driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().className(\""+parentClassName+"\")).scrollIntoView(new UiSelector().textContains(\""+childText+"\"));"));


	}
	public static void setValue(AndroidDriver<WebElement> driver,By by,String value)
	{
		driver.findElement(by).sendKeys(value);	
	}

	public static void tapByCoordinates(AndroidDriver<WebElement> driver,int x,int y) {
		TouchAction action=new TouchAction(driver);
		action.tap(PointOption.point(x, y)).perform();
	}

	public static void tapByElement(AndroidDriver<WebElement> driver,By by) {
		TouchAction action=new TouchAction(driver);
		action.tap(TapOptions.tapOptions().withElement(ElementOption.element(driver.findElement(by)))).perform();
	}

	public static String decoder(String text) {
		Decoder decode=Base64.getDecoder();
		return new String(decode.decode(text));
	}

	
	public static boolean verifyElementPresent(WebDriver driver,By by) {
		WebDriverWait wait=new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		if(driver.findElements(by).size()>0) {
			return true;
		}
		else {
			return false;
		}
	}

	public static String getText(WebDriver driver,By by) {
		WebDriverWait wait=new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		return driver.findElement(by).getText();

	}
	public static String getToastMessages(WebDriver driver,By by) {
		WebDriverWait wait=new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated((by)));
		return driver.findElement(by).getText();

	}
}

