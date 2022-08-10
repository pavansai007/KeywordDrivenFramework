package Automation.SampleKDF;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;



public class Keywords {
//	private static final int WebDriverWait = 10;

	//private static final String ObjectName = null;

	String path = System.getProperty("user.dir");
	
	 
	WebDriver driver;
	
	 
	public void enter_URL(WebDriver driver,String TestData) throws IOException{
	driver.get(TestData);
	}
	 
	public void type(WebDriver driver, String ObjectName, String locatorType, String testdata) throws IOException{
		
	 
	driver.findElement(this.getObject(locatorType,ObjectName)).sendKeys(testdata);
	//driver.findElement(By.xpath("//")).sendKeys(testdata);
	
	 
	}
	
	public void wait(WebDriver driver,String ObjectName, String locatorType) throws IOException, InterruptedException{
		
		
		
		WebDriverWait wait = new WebDriverWait (driver, Duration.ofSeconds((20)));
	wait.until(ExpectedConditions.invisibilityOf(driver.findElement(this.getObject( locatorType,ObjectName))));
	
	}
	public void click(WebDriver driver,String ObjectName, String locatorType) throws IOException{
	driver.findElement(this.getObject(locatorType,ObjectName)).click();
	}
	public String get_currentURL(WebDriver driver){
	String URL = driver.getCurrentUrl();
	System.out.println("print URL "+URL);
	return URL;
	}
	By getObject(String ObjectName, String locatorType) throws IOException{
	 
	
	//find by xpath
	if(locatorType.equalsIgnoreCase("XPATH")){
	 
	return By.xpath(ObjectName);
	}
	//find by class
	else if(locatorType.equalsIgnoreCase("className")){
	 
	return By.className(ObjectName);
	 
	}
	//find by name
	else if(locatorType.equalsIgnoreCase("NAME")){
	 
	return By.name(ObjectName);
	 
	}
	//Find by css
	else if(locatorType.equalsIgnoreCase("CSS")){
	 
	return By.cssSelector(ObjectName);
	 
	}
	//find by link
	else if(locatorType.equalsIgnoreCase("LINK")){
	 
	return By.linkText(ObjectName);
	 
	}
	//find by partial link
	else if(locatorType.equalsIgnoreCase("PARTIALLINK")){
	 
	return By.partialLinkText(ObjectName);
	 
	}
	return null;
	 
	}


}
