package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class TestBase {
  protected WebDriver driver;
  protected String baseURL = "https://automationexercise.com/";
  
  @BeforeClass
  public void openBrowser() {
	  driver = new ChromeDriver();
	  driver.manage().window().maximize();
	  driver.navigate().to(baseURL);
  }

  @AfterClass
  public void closeBrowser() {
	  driver.quit();
  }

}
