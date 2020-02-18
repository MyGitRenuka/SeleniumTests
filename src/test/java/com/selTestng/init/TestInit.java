package com.selTestng.init;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.selTestng.utility.Config;
import com.selTestng.utility.ExcelReader;
import com.selTestng.utility.Log;
import com.selTextng.reporter.ExtentManager;

/**
 * @author Renuka R Hosamani
 *
 * 
 */
public class TestInit {
	
	public static WebDriver driver;
	public static TimeUnit waitTimeUnit;
	public static long explicitWait;
	public static String browser;
	public static int screenshotCnt=0;
	private static final Logger log = Log.getLogger(TestInit.class);
	public static ExtentReports extent;
	public static ExtentTest test;	

	@BeforeSuite
	public void beforeSuite() throws Exception{
		log.info("######################### Test execution started #########################");
		extent = ExtentManager.getInstance();
		log.info("Extent report instance created" );
	}
	
	@BeforeTest
	public void setWebdriver() {
		Config.setProperties();
		browser = Config.testConfig.getProperty("browser").toLowerCase();
		switch(browser)
		{
			case "chrome":
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + 
						Config.testConfig.getProperty("chromeDriver"));	    
				System.out.println(System.getProperty("webdriver.chrome.driver"));
				TestInit.driver = new ChromeDriver();
				break;		    
			/*FIREFOX:
			IE:
			 */
			default: 
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + 
						Config.testConfig.getProperty("chromeDriver"));	    
				System.out.println(System.getProperty("webdriver.chrome.driver"));
				TestInit.driver = new ChromeDriver();							
		}		
		initialiseDriverWaits(driver);	
		test = extent.createTest(getClass().getSimpleName());
	}
	
	
	@AfterMethod
	public void afterMethod(ITestResult result) throws IOException{
		
		log.info("TEST || " + result.getName() + " || STATUS " + result.getStatus());
		if(result.getStatus() == ITestResult.FAILURE){
			test.log(Status.FAIL, result.getThrowable());
			String imagePath = captureScreen(driver,result.getName());
			test.addScreenCaptureFromPath(imagePath);
		}
		else if(result.getStatus() == ITestResult.SUCCESS){
			test.log(Status.PASS, result.getName()+" is pass");
		}
		else if(result.getStatus() == ITestResult.SKIP){
			test.log(Status.SKIP, result.getThrowable());
		}
		log.info("**************"+result.getName()+"Finished***************");
		extent.flush();
	}
	
	@AfterTest
	public void afterTest() throws Exception{
		if(driver!=null){
			driver.quit();
		}
		log.info("############# Test execution completed and all browser windows closed ############");
	}
	
	
	
	
	public void setWaitTimeUnit() {		
		String unitFromConfig = Config.testConfig.getProperty("timeUnit").toLowerCase();
		switch(unitFromConfig) {
		case "seconds":
			waitTimeUnit=TimeUnit.SECONDS;
		case "milliseconds":
			waitTimeUnit=TimeUnit.MILLISECONDS;
		case "microseconds":
			waitTimeUnit=TimeUnit.MICROSECONDS;
		case "minutes":
			waitTimeUnit=TimeUnit.MINUTES;
		default:
			waitTimeUnit=TimeUnit.SECONDS;		
		}	
		log.info("Wait time unit successfully set to " + unitFromConfig);
	}
	
	public void initialiseDriverWaits(WebDriver driver) {
		setWaitTimeUnit();
		setImplicitWait(driver);
		setPageLoadTime(driver);
		explicitWait=readWaitTimeFromConfig("explicitWait");
		deleteAllCookies(driver);
		log.info("Successfully initialised the webdriver");
	}
	
	public long readWaitTimeFromConfig(String key) {
		long value = Long.parseLong(Config.testConfig.get(key).toString());
		return value;	
	}
	
	public WebDriverWait setExplicitWait(WebDriver driver) {
		log.info("Creating Web driver wait object with explicit wait timeout of [" + explicitWait + "] " + 
						waitTimeUnit.name().toLowerCase());
		return  new WebDriverWait(driver, explicitWait);	
	}
	
	
	public WebDriverWait setCustomExplicitWait(WebDriver driver,long timeout) {
		log.info("Creating Web driver wait object with custom explicit wait timeout of[" +  timeout+"] " +
				waitTimeUnit.name().toLowerCase());
		return  new WebDriverWait(driver, timeout);	
	}
	
	public void setImplicitWait(WebDriver driver) {
		long timeout = readWaitTimeFromConfig("implicitWait");
		driver.manage().timeouts().implicitlyWait(timeout,waitTimeUnit);
		log.info(browser + " driver initialized with implicit wait time [" + timeout + "] " +
				waitTimeUnit.name().toLowerCase());
	}
	
	public void setPageLoadTime(WebDriver driver) {
		long timeout = readWaitTimeFromConfig("pageLoadTime");
		driver.manage().timeouts().pageLoadTimeout(timeout,waitTimeUnit);
		log.info(browser + " driver initialized with page load time [" + timeout +"] " + 
							waitTimeUnit.name().toLowerCase());
	}
	
	public void deleteAllCookies(WebDriver driver) {
		driver.manage().deleteAllCookies();
		log.info("Deleted all cookies");
	}

	
	public static void logExtentReport(String s1){
		test.log(Status.INFO, s1);
	}
	
	public void getApplicationUrl(String url){
		driver.get(url);
		logExtentReport("navigating to ..."+url);
	}
	
	
	public static String captureScreen(WebDriver driver, String fileName){
		if(driver == null){
			log.info("driver is null..");
			return null;
		}
		if(fileName==""){
			fileName = "blank";
		}
		Reporter.log("captureScreen method called");
		File destFile = null;
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		File screFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try{
			destFile = new File(Config.testConfig.getProperty("screenshotLoc")+"/"+
		fileName+"_"+formater.format(calendar.getTime())+".png");
			Files.copy(screFile.toPath(), destFile.toPath());
			
			Reporter.log("<a href='"+destFile.getAbsolutePath()+"'><img src='"+
			destFile.getAbsolutePath()+"'height='100' width='100'/></a>");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return destFile.toString();
	}
	
	public Object[][] getDataFromExcel(String file, String sheet,String key){
		String exceldataFile = Config.testConfig.getProperty("dataFilePath") + "/" + file;
		log.info("Reading data from excel data file: " + exceldataFile);
		ExcelReader excelDataReader = new ExcelReader();
		Object[][] data = excelDataReader.readDataFromExcelXSS(exceldataFile, sheet,key);
		return data;
	}
	
}
