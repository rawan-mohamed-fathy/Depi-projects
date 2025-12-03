package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import dataReader.LoadLoginNegativeDataProperties;
import pages.HomePage;
import pages.LoginPage;

public class Login_NegativeScenario extends TestBase {
	HomePage homeObject ;
	LoginPage loginObject;
	
	public void initializeObjects() {
		homeObject = new HomePage(driver);
		loginObject = new LoginPage(driver);
	}
	
	String email1 = LoadLoginNegativeDataProperties.loginNegativeData.getProperty("email1");
	String password1 = LoadLoginNegativeDataProperties.loginNegativeData.getProperty("password1");

	String email2 = LoadLoginNegativeDataProperties.loginNegativeData.getProperty("email2");
	String password2 = LoadLoginNegativeDataProperties.loginNegativeData.getProperty("password2");

	String email3 = LoadLoginNegativeDataProperties.loginNegativeData.getProperty("email3");
	String password3 = LoadLoginNegativeDataProperties.loginNegativeData.getProperty("password3");

	
	
	@DataProvider(name = "LoginNegativeData")
    public Object[][] getLoginNegativeData() {
		Object LoginNegativeData[][] = new Object[][]
       {
			{email1,password1},
			{email2,password2},
			{email3,password3},
        };
        return LoginNegativeData;
    }
	
    @Test (priority = 1,dataProvider = "LoginNegativeData")
    public void testLogin_WithInvalidEmailOrPassword(String email,String password) throws InterruptedException {
    	initializeObjects();
    	
    	//Thread.sleep(2000);
    	homeObject.openLoginPage();
    	Assert.assertEquals(loginObject.loginMessage.getText(), "Login to your account");
    	
    	//Thread.sleep(2000);
    	loginObject.userCanLogin(email,password);
    	
    	Assert.assertEquals(loginObject.errorMessage.getText(),"Your email or password is incorrect!");
    	//Thread.sleep(2000);
    }
    
}
