package testSet_01;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.BaseClass;
import uipages.CheckOutPage;
import uipages.HomePage;
import uipages.LoginPage;
import uipages.ProductPage;

public class TestCase_01 extends BaseClass {


	@BeforeClass
	public void setTestData() {
		file=System.getProperty("user.dir")+"\\TestData\\TestSet_01.xlsx"; //mention the file name of the test data sheet
		sheet="Sheet1";							//sheet name for test data
	}

	//The test script for the test case
	@Test(dataProvider = "readdata")
	public void test(Map<String,String> testData) throws IOException {
		String testID=testData.get("TestCaseID");

		test=report.createTest(testID);
		if(isEnabled(testData)) {
			try {


				test.info("Starting "+testID);
				String email=testData.get("Email");
				String password=testData.get("Password");	
				String item=testData.get("Product Name");
				String quantity=testData.get("Quantity");

				LoginPage login=new LoginPage(driver);
				HomePage home=new HomePage(driver);
				ProductPage product=new ProductPage(driver);
				CheckOutPage checkOut=new CheckOutPage(driver);


				if(login.login(email,password).verifyLogin()) {
					test.pass("Login successfully with email "+email, captureScreenshot());
				}
				else {
					test.fail("Login failed", captureScreenshot());
					Assert.fail();
				}

				if(home.searchItem(item).verifySearchResults()) {
					test.pass("Searched the item "+item+" successfully", captureScreenshot());
				}
				else {
					test.fail("Didn't search the item", captureScreenshot());
					Assert.fail();
				}

				if(home.clickOnSearchResult(item).verifyProductOpened(item)) {
					test.pass(item+" opened successfully", captureScreenshot());
				}
				else {
					test.fail(item+" not opened", captureScreenshot());
					Assert.fail();
				}
				if(product.clickOnDetails().openProductDetails()) {
					test.pass("product details opened", captureScreenshot());
				}
				else {
					test.fail("product details not opened", captureScreenshot());
					Assert.fail();
				}

				List<String> productDetails=product.getProductDetails();
				if(product.clickAddToCart()) {
					test.pass(productDetails.get(0)+" added to cart", captureScreenshot());
				}
				else {
					test.fail("not added to cart", captureScreenshot());
					Assert.fail();
				}

				if(product.clickGoToCart()) {
					if(checkOut.verifyCartPageIsOpened()) {
						test.pass("Cart checkout page is opened for "+item, captureScreenshot());
					}
					else {
						test.fail("Cart CheckOut page is not oepend", captureScreenshot());
					}
				}
				else {
					test.fail("Cart CheckOut page is not oepend", captureScreenshot());
				}
				
				List<String> productDetailsFromCartPage=checkOut.getProductDetailsFromCart();
				if(productDetailsFromCartPage.containsAll(productDetails)) {
					test.pass("Product details "+productDetails+" matched in checkout page", captureScreenshot());
				}
				else {
					test.fail("product details not matched "+productDetails, captureScreenshot());
					Assert.fail();
				}
				if(checkOut.selectProductQuantity(quantity)) {
					test.pass(item+" quantity selected as "+quantity, captureScreenshot());
				}
				else {
					test.fail("item quantity not selected", captureScreenshot());
					Assert.fail();
				}
				
				if(checkOut.removeItemBeforeUnisntalling()) {
					test.info("removed "+item+" from cart", captureScreenshot());
				}
				else {
					test.info("Failed to remove from cart "+item, captureScreenshot());
				}
			}
			catch(Exception E) {
				test.error(E.toString(),captureScreenshot());
			}
		}
		
		//if the flag is not Y, it will skip the particular test case if any is present in the test data sheet
		else {
			test.skip("Skipped "+testID);
			throw new SkipException(testID+" not enabled to run.Hence Skipped");
		}

		

	}

}