package com.plaid.base;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import com.plaid.utilities.ExcelReader;
import com.plaid.utilities.ExtentManager;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class TestBase {

		public static WebDriver driver;
		public static Properties OR = new Properties(); 
		public static Properties config = new Properties();
		public static final String projectPath = System.getProperty("user.dir");
		public static FileInputStream fisOR;
		public static FileInputStream fisConfig; 
		public static WebDriverWait wait;
		public static Logger log = Logger.getLogger("devpinoyLogger");
		public static ExcelReader excel= new ExcelReader(projectPath + "\\src\\test\\resources\\excel\\testPlaid.xlsx");
		public static ExtentTest test; 
		public static ExtentReports rep = ExtentManager.getInstance();
		
		
		@BeforeSuite 
		public void setUp() throws IOException {
			System.setProperty("org.uncommons.reportng.escape-output", "false");
			
			if(driver == null)	{
				fisOR = new FileInputStream(projectPath +"\\src\\test\\resources\\properties\\OR.properties");
				OR.load(fisOR);
				
				fisConfig=new FileInputStream(projectPath +"\\src\\test\\resources\\properties\\Config.properties");
				config.load(fisConfig);
			}
			
			if (config.getProperty("BROWSER").equals("firefox")) {
				  System.setProperty("webdriver.gecko.driver",   
				  projectPath + "\\src\\test\\resources\\executables\\geckodriver.exe");
				  driver = new FirefoxDriver();
				  log.debug("Firefox Lauched ....");
				}
			else if (config.getProperty("BROWSER").equals("chrome")) {
				 System.setProperty("webdriver.chrome.driver",   
				 projectPath + "\\src\\test\\resources\\executables\\chromedriver.exe");
				 driver = new ChromeDriver();
				 log.debug("Chrome Launched ....");
				} 
			else if (config.getProperty("BROWSER").equals("InternetExplorer")) {
				 System.setProperty("webdriver.ie.driver",
				 projectPath + "\\src\\test\\resources\\executables\\IEDriverServer.exe");
				 driver = new InternetExplorerDriver();
				}
			
			driver.get(config.getProperty("TEST_SITE_URL"));
			log.debug("Navigated to: " + config.getProperty("TEST_SITE_URL"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			wait = new WebDriverWait(driver,20);
			
		}
			
		//click
		public static void clickBy(By locator) {
			driver.findElement(locator).click();
		}
		
		//sendKeys
		public static void sendKeysBy(By locator, String input) {
			driver.findElement(locator).sendKeys(input);
		}
		
		//clear
		public static void clear(By locator) {
			driver.findElement(locator).clear();
		}
		
		//isSelected
		public static boolean isSelected(By locator) {
			boolean isSelected = driver.findElement(locator).isSelected();
			return isSelected;
		}
		
		//isDisplayed
		public static boolean isDisplayed(By locator) {
			boolean isDisplayed = driver.findElement(locator).isDisplayed();
			return isDisplayed;
		}
		
		//isEnabled
		public static boolean isEnabled(By locator) {
			boolean isEnabled = driver.findElement(locator).isEnabled();
			return isEnabled;
		}

		//StringClick
		public static void clickString(String locator) {
			driver.findElement(By.xpath(locator)).click();
		}

		//StringSendKeys
		public static void sendKeysString(String xpath, String input) {
			driver.findElement(By.xpath(xpath)).sendKeys(input);
		}
		
		//Upload an image
		public static void upload(String fileName) throws AWTException {
			Robot robot = new Robot();
			robot.setAutoDelay(2000);
			StringSelection stringselection = new StringSelection(projectPath + "\\src\\test\\resources\\testData\\" + fileName);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringselection, null);
			robot.setAutoDelay(2000);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.setAutoDelay(2000);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		}
		
		
		@DataProvider
		public Object[][] getData(){
			String sheetName = "userData";
			int rows = excel.getRowCount(sheetName);
			int cols = excel.getColumnCount(sheetName);
			Object[][] data = new Object[rows-1][cols];
			for (int rowNum=1; rowNum<= rows-1; rowNum++ ) {  
				for (int colNum=0; colNum<= cols-1; colNum++ ) {
			data[rowNum-1][colNum] = excel.getCellData(sheetName, colNum, rowNum);
				}//inner for 
			}//outer for 
			return data;
		}//public Object[][] getData()
		
		
		//Thread.sleep method 
		public static void delay(int milliSeconds) throws InterruptedException {
			Thread.sleep(milliSeconds);
		}
	
		@AfterSuite
		public void tearDown() {
			if (driver!= null) {
				driver.quit();
				log.debug("Test Successfully Completed");
			}
		}
		
	
	
}
