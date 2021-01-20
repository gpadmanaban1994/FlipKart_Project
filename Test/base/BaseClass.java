package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import utilities.BaseUtilities;

public class BaseClass {

	static protected AndroidDriver<WebElement> driver;


	static protected String app="";
	static protected String file=null;
	static protected String sheet=null;
	static private ExtentSparkReporter reporter;
	static protected ExtentReports report;
	static protected ExtentTest test; 
	public static String startDateTime=null;
	Properties prop;

	//reading test data file and sheet before each test method. the file and sheet name will be given in each class in before class method.
	@BeforeMethod                                     
	@DataProvider(name="readdata")
	public Object[][] setTestdata() throws IOException {

		return readData(file, sheet);
	}




	//this will return true or false based on if we set run flag as Y or N in test data.
	public boolean isEnabled(Map<String,String> map) {
		if(StringUtils.isNotBlank(map.get("RunFlag")) && map.get("RunFlag").equals("Y")) {
			return true;
		}
		else {
			return false;
		}
	}

	//starting the extent report and mentioning the path where the report are to be stored for each run
	@BeforeSuite
	public void setUp() {
		Date date=new Date();
		String datetime=new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss").format(date);
		startDateTime=datetime;
		reporter=new ExtentSparkReporter(System.getProperty("user.dir")+"//testreports//"+datetime+"//spark.html");
		report=new ExtentReports();
		report.attachReporter(reporter);



	}
	//extent report flush after the tests are done
	@AfterSuite
	public void tearDown() {


		report.flush();
	}

	//setting up url prop file and capabilties for driver session before starting with test
	@BeforeTest
	public void setTest() throws IOException {
		File file=new File(System.getProperty("user.dir")+"\\Flipkart.properties");
		FileInputStream in=new FileInputStream(file);
		prop=new Properties();
		prop.load(in);
		in.close();		
		DesiredCapabilities caps = DesiredCapabilities.android();
		caps.setCapability("appiumVersion", "1.18.1");
		caps.setCapability("deviceName","Google Pixel 3 GoogleAPI Emulator");
		caps.setCapability("deviceOrientation", "portrait");
		caps.setCapability("browserName", "");
		caps.setCapability("platformVersion","10.0");
		caps.setCapability("platformName","Android");
		caps.setCapability("app",prop.getProperty("app"));		
		driver=new AndroidDriver<>(new URL("https://mpgcristo:6aed154c-646b-4c98-970f-f57ccdb3c00c@ondemand.us-west-1.saucelabs.com:443/wd/hub"), caps);
		driver.manage().timeouts().implicitlyWait(15000, TimeUnit.MILLISECONDS);
		
	}

	@AfterTest
	public void closeDriver() {

		driver.removeApp(prop.getProperty("appPackage"));
		driver.quit();
	}

	//screenshot capture
	public MediaEntityModelProvider captureScreenshot() throws IOException {
		return BaseUtilities.captureScreenShot(driver);
	}
	//reading test data from excel
	public static Object[][] readData(String file,String sheet) throws IOException {


		File f=new File(file);
		FileInputStream fis=new FileInputStream(f);

		Workbook wb=new XSSFWorkbook(fis);
		Sheet sh=wb.getSheet(sheet);
		int totalRows=sh.getLastRowNum()+1;
		short totalColoumn=sh.getRow(0).getLastCellNum();
		Object[][] data=new Object[totalRows-1][1];
		DataFormatter df=new DataFormatter();
		for(int i=1;i<totalRows;i++) {

			Map<String,String> map=new HashMap<String, String>();

			for(int j=0;j<totalColoumn;j++) {
				map.put(df.formatCellValue(sh.getRow(0).getCell(j)), df.formatCellValue(sh.getRow(i).getCell(j)));			
			}
			data[i-1][0]=map;
		}
		return data;
	}


}
