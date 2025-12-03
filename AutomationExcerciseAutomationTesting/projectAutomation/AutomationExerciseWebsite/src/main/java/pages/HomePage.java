package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends PageBase {

	public HomePage(WebDriver driver) {
		super(driver);
	}
		
	@FindBy(linkText = "Signup / Login")
	WebElement signUpBtn;
	
	@FindBy(linkText = "Home")
	public WebElement homeBtn;
	
	@FindBy(id = "scrollUp")
	public WebElement ScrollUpArrowBtn;
	
	public void ScrollUpPage() {
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("const newsLetter = document.getElementById('');\r\n"
				+ "newsLetter.scrollIntoView(\r\n"
				+ "    {\r\n"
				+ "        behaviour : \"smooth\"\r\n"
				+ "    }\r\n"
				+ ");");
		
		ScrollUpArrowBtn.click();
	}
	
	public void openRegisterPage() {
		signUpBtn.click();
	}
	
	public void openLoginPage() {
		signUpBtn.click();
	}
	
	public void openHomePage() {
		homeBtn.click();
	}
	
	
}
