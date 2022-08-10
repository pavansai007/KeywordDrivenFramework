package Automation.SampleKDF;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.util.Timer.Status;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;




public class LoginPage {
	
	
	WebDriver driver;
	String path = System.getProperty("user.dir");
	
	
	Keywords keyword = new Keywords();
	
	ExtentReports extent;
	  ExtentReports reports;
      ExtentHtmlReporter htmlReporter;
      ExtentTest test;
	private com.aventstack.extentreports.Status Status;
    
      @BeforeTest
      public void startTest() {
                  reports = new ExtentReports();
                  htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "//Test Report//Extentreport.html");
                  reports.attachReporter(htmlReporter);
                  reports.setSystemInfo("Keyword Driven FrameWork", "Pavan sai");
                  
      }
   

	
	@Test
	public void readExcel() throws Exception  {
		
		//From excelfile
		String excelFilePath = path+ "\\External\\TestCases.xlsx";
		FileInputStream fileInputStream = new FileInputStream(excelFilePath);
		XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
	
		int testcasecount = workbook.getNumberOfSheets()-2;
		System.out.println("total test case : "+testcasecount);
		for (int testcase=0; testcase<testcasecount;testcase++) {
			System.setProperty(excelFilePath, excelFilePath);
			System.setProperty("webdriver.chrome.driver", path+"\\Driver\\chromedriver.exe");
			driver = new ChromeDriver();
			
			driver.manage().window().maximize();
		  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		 
			 XSSFSheet worksheet = workbook.getSheetAt(testcase);
			 System.out.println("worksheet Number" +testcase+":"+worksheet.getSheetName());
			 
			 int row = worksheet.getLastRowNum();
			int colum = worksheet.getRow(0).getLastCellNum();
			for(int i=1;i<=row;i++) {
				LinkedList<String> Testexecution = new LinkedList<>();
				 System.out.println("Row value :"+i+"It has first cell values as :"+worksheet.getRow(i).getCell(0));
				 for(int j=0;j<colum;j++ ) {
					 //System.out.println("Column index:"+j);
					Cell Criteria =  worksheet.getRow(i).getCell(j);
					
					String CriterialText;
					if(Criteria==null) {
						 CriterialText = null;
					}else {
						 CriterialText = Criteria.getStringCellValue();
					}
					Testexecution.add(CriterialText);
					//perform();
				 }
				System.out.println("List:" +Testexecution);
				 String TestStep = Testexecution.get(0);
				 String ObjectName = Testexecution.get(1);
				 String LocatorType = Testexecution.get(2);
				 String Testdata = Testexecution.get(3);
				 
				  
				perform(TestStep,ObjectName,LocatorType,Testdata);
				
				  
				 System.out.println("Row"+i+" is read and action performed");
			 }
			 //driver.close();
			 System.out.println("*************************************TestCases" +worksheet.getSheetName()+"is executed***********************************");
			 
		}
		
	}
	public void run() {
		
		
	}
	public void perform(String operation, String objectName, String locatorType, String testdata
			) throws IOException, InterruptedException {
		switch (operation) {
		
		case "enter_URL":
			//Perform click
			test = reports.createTest(testdata);
			keyword.enter_URL(driver,testdata);
			break;
		case "get_currentURL":
			//Set text on control
						keyword.get_currentURL(driver);
			break;
			 
			case "type":
				test = reports.createTest(testdata);
			keyword.type(driver, objectName, locatorType,testdata);
			 break;
			case "Click":
				test = reports.createTest(locatorType);
			keyword.click(driver, objectName, locatorType);
			break;
			case "logout":
				test = reports.createTest(locatorType);
				keyword.click(driver, objectName, locatorType);
			break;	
			
			case "Home":
				test = reports.createTest(locatorType);
				keyword.click(driver, objectName, locatorType);
				break;
			
			 
			default:
		
			
		}
		}
	 @AfterMethod
     public void setTestResult(ITestResult result) throws IOException {
                                  if (result.getStatus() == ITestResult.FAILURE) {
                             test.log(Status.FAIL, result.getName());
                             test.log(Status.FAIL,result.getThrowable());
               
                 } else if (result.getStatus() == ITestResult.SUCCESS) {
                             test.log(Status.PASS, result.getName());
                            
                 } else if (result.getStatus() == ITestResult.SKIP) {
                             test.skip("Test Case : " + result.getName() + " has been skipped");
                 }
                 reports.flush();
                 driver.close();
     }
     @AfterTest
     public void endTest() {
                
                 reports.flush();
     }
 
   
		}
		
	


